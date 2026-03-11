package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 配额使用请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class QuotaUseRequest {

    /**
     * 配额ID
     */
    private Long quotaId;

    /**
     * 使用数量
     */
    private Integer useCount;
}
