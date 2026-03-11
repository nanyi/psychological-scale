package com.iotsic.ps.order.dto;

import lombok.Data;

import java.math.BigDecimal;

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
     * 量表ID
     */
    private Long scaleId;

    /**
     * 配额数量
     */
    private Integer quota;

    /**
     * 价格
     */
    private BigDecimal price;
}
