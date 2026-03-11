package com.iotsic.ps.user.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.user.dto.TokenRefreshResponse;
import com.iotsic.ps.user.dto.UserLoginRequest;
import com.iotsic.ps.user.dto.UserLoginResponse;
import com.iotsic.ps.user.dto.UserRegisterRequest;
import com.iotsic.ps.user.dto.UserRegisterResponse;
import com.iotsic.ps.user.service.UserService;
import com.iotsic.ps.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 * 负责用户注册、登录、信息查询等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 根据ID获取用户信息
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public RestResult<UserVO> getUserById(@PathVariable Long id) {
        return RestResult.success(userService.getUserInfo(id));
    }

    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/username/{username}")
    public RestResult<UserVO> getUserByUsername(@PathVariable String username) {
        return RestResult.success(userService.getUserInfo(userService.getUserByUsername(username).getId()));
    }

    /**
     * 根据手机号获取用户信息
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    @GetMapping("/phone/{phone}")
    public RestResult<UserVO> getUserByPhone(@PathVariable String phone) {
        return RestResult.success(userService.getUserInfo(userService.getUserByPhone(phone).getId()));
    }

    /**
     * 用户注册
     * 
     * @param request 注册请求
     * @return 注册结果
     */
    @PostMapping("/register")
    public RestResult<UserRegisterResponse> register(@RequestBody UserRegisterRequest request) {
        Map<String, Object> result = userService.register(
                request.getUsername(),
                request.getPassword(),
                request.getPhone(),
                request.getEmail()
        );

        UserRegisterResponse response = new UserRegisterResponse();
        response.setUserId((Long) result.get("userId"));
        response.setUsername(request.getUsername());

        return RestResult.success(response);
    }

    /**
     * 用户登录
     * 
     * @param request 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public RestResult<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        Map<String, Object> result = userService.login(
                request.getUsername(),
                request.getPassword(),
                null
        );

        UserLoginResponse response = new UserLoginResponse();
        response.setUserId((Long) result.get("userId"));
        response.setUsername((String) result.get("username"));
        response.setToken((String) result.get("token"));

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
        Map<String, Object> result = userService.refreshToken(refreshToken);
        
        TokenRefreshResponse response = new TokenRefreshResponse();
        response.setAccessToken((String) result.get("accessToken"));
        response.setRefreshToken((String) result.get("refreshToken"));
        response.setExpiresIn((Long) result.get("expiresIn"));
        
        return RestResult.success(response);
    }

    /**
     * 获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/info/{userId}")
    public RestResult<UserVO> getUserInfo(@PathVariable Long userId) {
        return RestResult.success(userService.getUserInfo(userId));
    }
}
