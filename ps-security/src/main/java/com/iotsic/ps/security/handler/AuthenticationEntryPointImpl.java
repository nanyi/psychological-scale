package com.iotsic.ps.security.handler;

import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.common.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.error("Authentication failed: {} - {}", request.getRequestURI(), authException.getMessage());

        String message = "Authentication failed";
        if (authException instanceof BadCredentialsException) {
            message = "Invalid username or password";
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        RestResult<Void> result = RestResult.fail(ErrorCodeEnum.UNAUTHORIZED.getCode(), message);
        response.getWriter().write(JsonUtils.toJson(result));
    }
}
