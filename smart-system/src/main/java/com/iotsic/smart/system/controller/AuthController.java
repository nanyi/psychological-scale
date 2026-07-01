package com.iotsic.smart.system.controller;

import com.iotsic.ps.core.entity.OAuth2AccessToken;
import com.iotsic.smart.system.dto.AuthResultDTO;
import com.iotsic.smart.system.dto.OAuth2AccessTokenResponse;
import com.iotsic.smart.system.dto.TokenRefreshResponse;
import com.iotsic.smart.system.dto.UserLoginRequest;
import com.iotsic.smart.system.dto.UserLoginResponse;
import com.iotsic.smart.system.service.AuthService;
import com.iotsic.smart.system.service.UserService;
import com.iotsic.smart.system.service.oauth2.OAuth2TokenService;
import com.iotsic.smart.system.vo.LoginUserVO;
import com.iotsic.smart.system.vo.UserVO;
import com.iotsic.smart.framework.common.result.RestResult;
import com.iotsic.smart.framework.common.utils.BeanUtils;
import com.iotsic.smart.framework.common.utils.http.HttpUtils;
import com.iotsic.smart.framework.common.utils.web.ServletUtils;
import com.iotsic.smart.framework.tenant.constant.TenantConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 认证控制器
 * 负责用户登录、信息查询等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final OAuth2TokenService oauth2TokenService;

    /**
     * 用户登录
     * 
     * @param request 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public RestResult<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String deviceId = UUID.randomUUID().toString();

        AuthResultDTO result = authService.login(
                request.getTenantId() == null ? TenantConstants.DEFAULT_TENANT_ID : request.getTenantId(),
                request.getUsername(),
                request.getPassword(),
                ServletUtils.getClientIP(),
                deviceId
        );

        UserVO user = userService.getUserInfo(result.getUserId());
        LoginUserVO loginUser = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUser);

        UserLoginResponse response = new UserLoginResponse();
        response.setUser(loginUser);
        response.setToken(result.getToken());

        return RestResult.success(response);
    }

    /**
     * 用户登出
     *
     * @return 操作结果
     */
    @PostMapping("/logout")
    public RestResult<Void> logout() {
        authService.logout();
        return RestResult.success();
    }

    /**
     * 刷新Token
     * 
     * @param refreshToken 刷新令牌
     * @return 新Token
     */
    @PostMapping("/refresh-token")
    public RestResult<TokenRefreshResponse> refreshToken(@RequestParam String refreshToken) {
        AuthResultDTO result = authService.refreshToken(refreshToken);
        
        TokenRefreshResponse response = new TokenRefreshResponse();
        response.setAccessToken(result.getToken());
        response.setRefreshToken(result.getRefreshToken());
        response.setExpiresIn(result.getExpiresIn());
        
        return RestResult.success(response);
    }

    @GetMapping("/check-token")
    @Operation(summary = "校验访问令牌")
    @Parameter(name = "accessToken", description = "访问令牌", required = true)
    public RestResult<OAuth2AccessTokenResponse> checkAccessToken(@RequestParam("accessToken") String accessToken) {
        OAuth2AccessToken response = oauth2TokenService.checkAccessToken(accessToken);
        return RestResult.success(BeanUtils.toBean(response, OAuth2AccessTokenResponse.class));
    }
}
