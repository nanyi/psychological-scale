package com.iotsic.ps.user.dto;

import lombok.Data;

/**
 * 用户登录响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserLoginResponse {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * Token
     */
    private String token;
}
