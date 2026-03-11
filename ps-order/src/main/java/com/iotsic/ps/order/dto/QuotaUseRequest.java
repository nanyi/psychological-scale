package com.iotsic.ps.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 配额使用请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class QuotaUseRequest {

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 量表ID
     */
    private Long scaleId;
}
