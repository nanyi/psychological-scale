package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 配额创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class QuotaCreateRequest {

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 配额数量
     */
    private Integer quantity;
}
