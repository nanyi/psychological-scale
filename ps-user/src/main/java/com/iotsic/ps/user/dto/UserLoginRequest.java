package com.iotsic.ps.user.dto;

import lombok.Data;

/**
 * 用户登录请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserLoginRequest {

    /**
     * 用户名或手机号
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
