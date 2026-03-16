package com.iotsic.ps.user.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.user.dto.AuthResultDTO;
import com.iotsic.ps.user.dto.UserRegisterRequest;
import com.iotsic.ps.user.dto.UserRegisterResponse;
import com.iotsic.ps.user.service.UserService;
import com.iotsic.ps.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 * 负责用户注册、信息查询等请求
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
    @GetMapping("/info/{id}")
    public RestResult<UserVO> getUserById(@PathVariable Long id) {
        return RestResult.success(userService.getUserInfo(id));
    }

    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/by-username/{username}")
    public RestResult<UserVO> getUserByUsername(@PathVariable String username) {
        return RestResult.success(userService.getUserInfo(userService.getUserByUsername(username).getId()));
    }

    /**
     * 根据手机号获取用户信息
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    @GetMapping("/by-phone/{phone}")
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
        AuthResultDTO result = userService.register(
                request.getUsername(),
                request.getPassword(),
                request.getPhone(),
                request.getEmail()
        );

        UserRegisterResponse response = new UserRegisterResponse();
        response.setUserId(result.getUserId());
        response.setUsername(request.getUsername());

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
