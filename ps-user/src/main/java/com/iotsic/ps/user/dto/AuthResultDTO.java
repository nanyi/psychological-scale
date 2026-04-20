package com.iotsic.ps.user.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 认证结果DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
@Accessors(chain = true)
public class AuthResultDTO {

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 令牌过期时间（秒）
     */
    private Long expiresIn;

}
