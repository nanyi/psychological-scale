package com.iotsic.ps.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 退款创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class RefundCreateRequest {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;
}
