package com.iotsic.ps.api.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 退款订单请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class RefundOrderRequest {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    private String reason;
}