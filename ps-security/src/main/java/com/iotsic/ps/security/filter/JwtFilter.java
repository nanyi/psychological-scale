package com.iotsic.ps.security.filter;

import com.iotsic.ps.common.constant.SystemConstant;
import com.iotsic.ps.common.utils.JsonUtils;
import com.iotsic.ps.security.service.JwtService;
import com.iotsic.ps.security.service.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);
            if (StringUtils.hasText(token) && jwtService.validateToken(token)) {
                String redisKey = SystemConstant.REDIS_TOKEN_PREFIX + jwtService.getUserId(token);
                Object cachedToken = redisTemplate.opsForValue().get(redisKey);

                if (cachedToken != null && cachedToken.equals(token)) {
                    Long userId = jwtService.getUserId(token);
                    String username = jwtService.getUsername(token);

                    String userKey = SystemConstant.REDIS_USER_PREFIX + userId;
                    Object userObj = redisTemplate.opsForValue().get(userKey);

                    if (userObj != null) {
                        UserDetailsImpl userDetails = JsonUtils.fromJson(
                                JsonUtils.toJson(userObj), UserDetailsImpl.class);

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            log.error("JWT filter error: {}", e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(SystemConstant.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SystemConstant.TOKEN_PREFIX)) {
            return bearerToken.substring(SystemConstant.TOKEN_PREFIX.length());
        }
        return null;
    }
}
