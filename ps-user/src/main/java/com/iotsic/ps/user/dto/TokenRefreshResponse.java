package com.iotsic.ps.user.dto;

import lombok.Data;

/**
 * Token刷新响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class TokenRefreshResponse {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 过期时间（秒）
     */
    private Long expiresIn;
}
