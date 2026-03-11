package com.iotsic.ps.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 企业配额充值请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class QuotaRechargeRequest {

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 充值数量
     */
    private Integer quantity;

    /**
     * 支付金额
     */
    private BigDecimal amount;
}
