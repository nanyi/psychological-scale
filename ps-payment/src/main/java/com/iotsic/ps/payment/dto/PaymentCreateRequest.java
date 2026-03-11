package com.iotsic.ps.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@Data
public class PaymentCreateRequest {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 订单描述
     */
    private String description;
}
