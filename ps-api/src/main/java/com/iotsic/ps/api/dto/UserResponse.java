package com.iotsic.ps.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserResponse {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 (0-禁用, 1-正常)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
