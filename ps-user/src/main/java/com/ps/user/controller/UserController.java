package com.iotsic.ps.user.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.user.service.UserService;
import com.iotsic.ps.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public RestResult<Map<String, Object>> getUserById(@PathVariable Long id) {
        return RestResult.success(userService.getUserInfo(id));
    }

    @GetMapping("/username/{username}")
    public RestResult<Map<String, Object>> getUserByUsername(@PathVariable String username) {
        return RestResult.success(userService.getUserInfo(userService.getUserByUsername(username).getId()));
    }

    @GetMapping("/phone/{phone}")
    public RestResult<Map<String, Object>> getUserByPhone(@PathVariable String phone) {
        return RestResult.success(userService.getUserInfo(userService.getUserByPhone(phone).getId()));
    }

    @PostMapping("/register")
    public RestResult<Map<String, Object>> register(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        String phone = (String) request.get("phone");
        String email = (String) request.get("email");

        Map<String, Object> result = userService.register(username, password, phone, email);
        return RestResult.success("注册成功", result);
    }

    @PostMapping("/login")
    public RestResult<Map<String, Object>> login(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        String loginIp = (String) request.get("loginIp");

        Map<String, Object> result = userService.login(username, password, loginIp);
        return RestResult.success("登录成功", result);
    }

    @PostMapping("/logout")
    public RestResult<Void> logout(@RequestParam Long userId) {
        userService.logout(userId);
        return RestResult.success();
    }

    @PostMapping("/refresh-token")
    public RestResult<Map<String, Object>> refreshToken(@RequestParam String refreshToken) {
        Map<String, Object> result = userService.refreshToken(refreshToken);
        return RestResult.success(result);
    }

    @GetMapping("/info/{userId}")
    public RestResult<UserVO> getUserInfo(@PathVariable Long userId) {
        return RestResult.success(userService.getUserInfo(userId));
    }
}
