package com.iotsic.ps.api.user;

import com.iotsic.ps.api.config.FeignConfig;
import com.iotsic.ps.api.dto.TokenResponse;
import com.iotsic.ps.api.dto.UserLoginRequest;
import com.iotsic.ps.api.dto.UserRegisterRequest;
import com.iotsic.ps.api.dto.UserResponse;
import com.iotsic.smart.framework.common.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ps-user", contextId = "userApi", configuration = FeignConfig.class)
public interface UserApi {

    @GetMapping("/api/user/info/{id}")
    RestResult<UserResponse> getUserById(@PathVariable("id") Long id);

    @GetMapping("/api/user/by-username/{username}")
    RestResult<UserResponse> getUserByUsername(@PathVariable("username") String username);

    @GetMapping("/api/user/by-phone/{phone}")
    RestResult<UserResponse> getUserByPhone(@PathVariable("phone") String phone);

    @PostMapping("/api/user/register")
    RestResult<UserResponse> register(@RequestBody UserRegisterRequest request);

    @PostMapping("/api/user/login")
    RestResult<TokenResponse> login(@RequestBody UserLoginRequest request);

    @PostMapping("/api/user/logout")
    RestResult<Void> logout(@RequestParam("userId") Long userId);

    @PostMapping("/api/user/refresh-token")
    RestResult<TokenResponse> refreshToken(@RequestParam("refreshToken") String refreshToken);
}
