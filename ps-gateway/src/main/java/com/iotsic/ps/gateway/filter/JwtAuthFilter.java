package com.iotsic.ps.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * JWT认证全局过滤器
 * 验证请求头中的JWT Token，解析用户信息并传递给下游服务
 *
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret:psychological-scale-secret-key-2026}")
    private String jwtSecret;

    @Value("${jwt.issuer:psychological-scale}")
    private String jwtIssuer;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (path.startsWith("/api/auth/") || path.startsWith("/api/user/register")) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(AUTH_HEADER);

        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(BEARER_PREFIX)) {
            return unauthorized(exchange.getResponse(), "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(BEARER_PREFIX.length());

        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .requireIssuer(jwtIssuer)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Long userId = claims.get("userId", Long.class);
            String username = claims.getSubject();

            if (!StringUtils.hasText(username)) {
                return unauthorized(exchange.getResponse(), "Invalid token: missing username");
            }

            ServerHttpRequest mutatedRequest = request.mutate()
                    .header(USER_ID_HEADER, userId.toString())
                    .header(USER_NAME_HEADER, username)
                    .build();

            log.debug("Authenticated user: {} for path: {}", userId, path);

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            log.warn("JWT validation failed for path: {}, error: {}", path, e.getMessage());
            return unauthorized(exchange.getResponse(), "Invalid or expired token");
        }
    }

    private Mono<Void> unauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String body = String.format("{\"code\":401,\"message\":\"%s\"}", message);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8))));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
