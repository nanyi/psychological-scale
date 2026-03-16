package com.iotsic.ps.user.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.user.dto.AuthResultDTO;
import com.iotsic.ps.user.dto.TokenRefreshResponse;
import com.iotsic.ps.user.dto.UserLoginRequest;
import com.iotsic.ps.user.dto.UserLoginResponse;
import com.iotsic.ps.user.service.UserService;
import com.iotsic.ps.user.vo.LoginUserVO;
import com.iotsic.ps.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    private final UserService userService;

    /**
     * 用户登录
     * 
     * @param request 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public RestResult<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        AuthResultDTO result = userService.login(
                request.getUsername(),
                request.getPassword(),
                null
        );

        UserLoginResponse response = new UserLoginResponse();
        UserVO user = userService.getUserInfo(result.getUserId());
        LoginUserVO loginUser = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUser);
        response.setUser(loginUser);
        response.setToken(result.getToken());

        return RestResult.success(response);
    }

    /**
     * 用户登出
     * 
     * @param userId 用户ID
     * @return 操作结果
     */
    @PostMapping("/logout")
    public RestResult<Void> logout(@RequestParam Long userId) {
        userService.logout(userId);
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
        AuthResultDTO result = userService.refreshToken(refreshToken);
        
        TokenRefreshResponse response = new TokenRefreshResponse();
        response.setAccessToken(result.getToken());
        response.setRefreshToken(result.getRefreshToken());
        response.setExpiresIn(result.getExpiresIn());
        
        return RestResult.success(response);
    }
}
