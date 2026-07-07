package com.iotsic.smart.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.iotsic.smart.framework.common.exception.enums.GlobalResultCode;
import com.iotsic.smart.framework.common.result.RestResult;
import com.iotsic.smart.framework.common.utils.BeanUtils;
import com.iotsic.smart.framework.common.utils.json.JsonUtils;
import com.iotsic.smart.gateway.config.properties.SecurityProperties;
import com.iotsic.smart.gateway.dto.LoginUser;
import com.iotsic.smart.gateway.dto.OAuth2AccessTokenResponse;
import com.iotsic.smart.gateway.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * JWT认证全局过滤器
 * 验证请求头中的JWT Token，解析用户信息并传递给下游服务
 *
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@Component
public class TokenAuthenticationFilter extends FilterStatus implements GlobalFilter {

    private final SecurityProperties properties;

    private final WebClient webClient;

    /**
     * 登录用户的本地缓存
     *
     * key1：多租户的编号
     * key2：访问令牌
     */
    private final LoadingCache<String, LoginUser> loginUserCache = buildCache(Duration.ofMinutes(1L));

    /**
     * 异步刷新的 LoadingCache 最大缓存数量
     *
     * @see <a href="">本地缓存 CacheUtils 工具类建议</a>
     */
    private static final Integer CACHE_MAX_SIZE = 10000;

    /**
     * 构建同步刷新的 LoadingCache 对象
     *
     * @param duration 过期时间
     * @return LoadingCache 对象
     */
    private LoadingCache<String, LoginUser> buildCache(Duration duration) {
        return CacheBuilder.newBuilder()
                .maximumSize(CACHE_MAX_SIZE)
                // 只阻塞当前数据加载线程，其他线程返回旧值
                .refreshAfterWrite(duration)
                .build(new CacheLoader<String, LoginUser>() {
                    @Override
                    public LoginUser load(String key) {
                        Mono<LoginUser> userMono = checkAccessToken(key)
                                .flatMap(body -> {
                            LoginUser remoteUser = buildUser(body);
                            if (remoteUser != null) {
                                return Mono.just(remoteUser);
                            }
                            return Mono.empty();
                        });
                        return userMono.block();
                    }
                });
    }

    public TokenAuthenticationFilter(SecurityProperties properties, ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.properties = properties;
        this.webClient = WebClient.builder().filter(lbFunction).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String token = SecurityUtils.getToken(exchange);
        if (StrUtil.isEmpty(token)) {
            return chain.filter(exchange);
        }

        try {
            return getCurrentUser(token)
                    .defaultIfEmpty(new LoginUser()).flatMap(user -> {
                        // 1. 无用户，直接 filter 继续请求
                        if (user.getUserId() == null || user.getUserId() <= 0) {
                            return chain.filter(exchange);
                        }

                        // 2.1 有用户，则设置登录用户
                        SecurityUtils.setCurrentUser(exchange, user);

                        // 2.2 将 user 设置到 请求头
                        ServerWebExchange newExchange = exchange.mutate()
                                .request(builder -> SecurityUtils.setCurrentUserHeader(builder, user)).build();

                        log.debug("Authenticated user: {} for path: {}", user.getUserId(), request.getURI());

                        return chain.filter(newExchange);
                    });
        } catch (Exception e) {
            log.warn("Token validation failed for path: {}, error: {}", request.getURI(), e.getMessage());
            return unauthorized(exchange.getResponse(), "Invalid or expired token");
        }
    }

    private Mono<Void> unauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String body = String.format("{\"code\":401,\"message\":\"%s\"}", message);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8))));
    }

    private Mono<LoginUser> getCurrentUser(String token) {
        // 从缓存中，获取 LoginUser
        LoginUser localUser = loginUserCache.getIfPresent(token);
        if (localUser != null) {
            return Mono.just(localUser);
        }

        // 缓存不存在，则请求远程服务
        return checkAccessToken(token)
                .flatMap((Function<RestResult<OAuth2AccessTokenResponse>, Mono<LoginUser>>) body -> {
                    LoginUser remoteUser = buildUser(body);
                    if (remoteUser != null) {
                        // 非空，则进行缓存
                        loginUserCache.put(token, remoteUser);
                        return Mono.just(remoteUser);
                    }
                    return Mono.empty();
                });
    }

    private Mono<RestResult<OAuth2AccessTokenResponse>> checkAccessToken(String token) {
        return webClient
                .get()
                .uri(properties.getCheckUrl(), uriBuilder -> uriBuilder.queryParam("accessToken", token).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResult<OAuth2AccessTokenResponse>>() {
                });
    }

    private LoginUser buildUser(RestResult<OAuth2AccessTokenResponse> result) {
        if (result == null) {
            return null;
        }
        if (GlobalResultCode.SUCCESS.getCode() != result.getCode()) {
            if (Objects.equals(result.getCode(), HttpStatus.UNAUTHORIZED.value())) {
                return new LoginUser();
            }
            return null;
        }

        // 创建登录用户
        OAuth2AccessTokenResponse tokenInfo = result.getData();
        if (tokenInfo == null) {
            return null;
        }
        return new LoginUser()
                .setUserId(tokenInfo.getUserId())
                .setUsername(tokenInfo.getUserInfo().get("username"))
                .setUserType(tokenInfo.getUserType())
                .setTenantId(tokenInfo.getTenantId())
                .setScopes(tokenInfo.getScopes());
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 80;
    }
}
