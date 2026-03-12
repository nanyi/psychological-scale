package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 支付状态响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class PaymentStatusResponse {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 支付状态
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 支付金额
     */
    private java.math.BigDecimal amount;

    /**
     * 支付时间
     */
    private java.time.LocalDateTime payTime;

    /**
     * 第三方交易号
     */
    private String transactionId;
}
