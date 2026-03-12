package com.iotsic.ps.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 购买检查响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class PurchaseCheckResponse {

    /**
     * 是否已购买
     */
    private Boolean hasPurchased;

    /**
     * 剩余配额
     */
    private Integer remainingQuota;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
}
