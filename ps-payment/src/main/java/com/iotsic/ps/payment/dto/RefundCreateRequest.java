package com.iotsic.ps.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 退款创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@Data
public class RefundCreateRequest {

    /**
     * 支付记录ID
     */
    private Long paymentId;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;
}
