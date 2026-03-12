package com.iotsic.ps.api.dto;

import lombok.Data;

/**
 * 令牌响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class TokenResponse {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 过期时间(秒)
     */
    private Long expiresIn;

    /**
     * 令牌类型 (Bearer)
     */
    private String tokenType;
}
