package com.iotsic.smart.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.iotsic.smart.gateway.config.properties.SecurityProperties;
import com.iotsic.smart.gateway.dto.LoginUser;
import com.iotsic.smart.gateway.utils.SecurityUtils;
import com.iotsic.smart.framework.common.exception.enums.GlobalResultCode;
import com.iotsic.smart.framework.common.result.RestResult;
import com.iotsic.smart.framework.common.utils.CollectionUtils;
import com.iotsic.smart.framework.common.utils.ConvertUtils;
import com.iotsic.smart.framework.common.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * JWT认证全局过滤器
 * 验证请求头中的JWT Token，解析用户信息并传递给下游服务
 *
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@Component
public class TokenAuthenticationFilter implements GlobalFilter, Ordered {

    private final SecurityProperties properties;

    private final WebClient webClient;

    /**
     * 登录用户的本地缓存
     *
     * key1：多租户的编号
     * key2：访问令牌
     */
    private final LoadingCache<Set<String>, LoginUser> loginUserCache = buildCache(Duration.ofMinutes(1L));

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
    private LoadingCache<Set<String>, LoginUser> buildCache(Duration duration) {
        return CacheBuilder.newBuilder()
                .maximumSize(CACHE_MAX_SIZE)
                // 只阻塞当前数据加载线程，其他线程返回旧值
                .refreshAfterWrite(duration)
                .build(new CacheLoader<Set<String>, LoginUser>() {
                    @Override
                    public LoginUser load(Set<String> map) {
                        for (String key : map) {
                            String body = checkAccessToken(key).block();
                            return buildUser(body);
                        }
                        return null;
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

        String token = SecurityUtils.obtainAuthorization(exchange);
        if (StrUtil.isEmpty(token)) {
            return chain.filter(exchange);
        }

        try {
            return getLoginUser(exchange, token).defaultIfEmpty(new LoginUser()).flatMap(user -> {
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

    private Mono<LoginUser> getLoginUser(ServerWebExchange exchange, String token) {
        // 从缓存中，获取 LoginUser
        Set<String> cacheKey = new HashSet<>(1);
        cacheKey.add(token);
        LoginUser localUser = loginUserCache.getIfPresent(cacheKey);
        if (localUser != null) {
            return Mono.just(localUser);
        }

        // 缓存不存在，则请求远程服务
        return checkAccessToken(token).flatMap((Function<String, Mono<LoginUser>>) body -> {
            LoginUser remoteUser = buildUser(body);
            if (remoteUser != null) {
                // 非空，则进行缓存
                loginUserCache.put(cacheKey, remoteUser);
                return Mono.just(remoteUser);
            }
            return Mono.empty();
        });
    }

    private Mono<String> checkAccessToken(String token) {
        return webClient
                .get()
                .uri(properties.getCheckUrl(), uriBuilder -> uriBuilder.queryParam("accessToken", token).build())
                .retrieve()
                .bodyToMono(String.class);
    }

    private LoginUser buildUser(String body) {
        RestResult<String> result = JsonUtils.parseObject(body, new TypeReference<RestResult<String>>() {
        });
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
        Map<String, Object> tokenInfo = JsonUtils.parseObject(result.getData(), new TypeReference<HashMap<String, Object>>() {
        });
        if (tokenInfo == null) {
            return null;
        }
        return new LoginUser()
                .setUserId(ConvertUtils.toLong(tokenInfo.get("userId")))
                .setUserType(ConvertUtils.toInt(tokenInfo.get("userType")))
                .setTenantId(ConvertUtils.toStr(tokenInfo.get("tenantId")))
                .setScopes(JsonUtils.parseObject(JsonUtils.toJSONString(tokenInfo.get("scopes")), new TypeReference<List<String>>() {
                }));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
