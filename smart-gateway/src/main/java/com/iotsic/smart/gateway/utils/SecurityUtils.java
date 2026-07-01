package com.iotsic.smart.gateway.utils;

import com.iotsic.smart.gateway.config.properties.SecurityProperties;
import com.iotsic.smart.gateway.dto.LoginUser;
import com.iotsic.smart.framework.common.utils.SpringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 安全服务工具类
 *
 * @author Ryan
 * @date 2026-04-13 15:38
 */
@Slf4j
public class SecurityUtils {

    private static final SecurityProperties PROPERTIES = SpringUtils.getBean(SecurityProperties.class);

    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_NAME_HEADER = "X-User-Name";
    private static final String USER_TYPE_HEADER = "X-User-Type";

    /**
     * 从请求中，获得认证 Token
     *
     * @param exchange 请求
     * @return 认证 Token
     */
    public static String obtainAuthorization(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(PROPERTIES.getTokenHeader());
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(PROPERTIES.getTokenPrefix())) {
            return null;
        }

        return authHeader.substring(PROPERTIES.getTokenPrefix().length()).trim();
    }

    /**
     * 设置登录用户
     *
     * @param exchange 请求
     * @param user 用户
     */
    public static void setCurrentUser(ServerWebExchange exchange, LoginUser user) {
        exchange.getAttributes().put(USER_ID_HEADER, user.getUserId());
        exchange.getAttributes().put(USER_NAME_HEADER, user.getUsername());
        exchange.getAttributes().put(USER_TYPE_HEADER, user.getUserType());
    }


    /**
     * 将 user 设置到 请求头
     *
     * @param builder 请求
     * @param user 用户
     */
    @SneakyThrows
    public static void setCurrentUserHeader(ServerHttpRequest.Builder builder, LoginUser user) {
        try {
            builder.header(USER_ID_HEADER, URLEncoder.encode(user.getUserId().toString(), StandardCharsets.UTF_8));
            builder.header(USER_NAME_HEADER, URLEncoder.encode(user.getUsername(), StandardCharsets.UTF_8));
            builder.header(USER_TYPE_HEADER, URLEncoder.encode(user.getUserType().toString(), StandardCharsets.UTF_8));
        } catch (Exception ex) {
            log.error("[setCurrentUserHeader][序列化 user({}) 发生异常]", user, ex);
            throw ex;
        }
    }
}
