package com.iotsic.smart.system.dto;

import lombok.Data;

/**
 * 用户注册响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserRegisterResponse {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;
}
