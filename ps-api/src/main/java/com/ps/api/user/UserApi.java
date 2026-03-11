package com.ps.api.user;

import com.ps.api.config.FeignConfig;
import com.ps.common.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "ps-user", contextId = "userApi", configuration = FeignConfig.class)
public interface UserApi {

    @GetMapping("/api/user/{id}")
    RestResult<Map<String, Object>> getUserById(@PathVariable("id") Long id);

    @GetMapping("/api/user/username/{username}")
    RestResult<Map<String, Object>> getUserByUsername(@PathVariable("username") String username);

    @GetMapping("/api/user/phone/{phone}")
    RestResult<Map<String, Object>> getUserByPhone(@PathVariable("phone") String phone);

    @PostMapping("/api/user/register")
    RestResult<Map<String, Object>> register(@RequestBody Map<String, Object> request);

    @PostMapping("/api/user/login")
    RestResult<Map<String, Object>> login(@RequestBody Map<String, Object> request);

    @PostMapping("/api/user/logout")
    RestResult<Void> logout(@RequestParam("userId") Long userId);

    @PostMapping("/api/user/refresh-token")
    RestResult<Map<String, Object>> refreshToken(@RequestParam("refreshToken") String refreshToken);
}
