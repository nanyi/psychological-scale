package com.iotsic.ps.api.dto;

import lombok.Data;

/**
 * 用户注册请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserRegisterRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
