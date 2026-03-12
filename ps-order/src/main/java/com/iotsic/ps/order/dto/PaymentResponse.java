package com.iotsic.ps.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付创建响应DTO
 *
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class PaymentResponse {

    /**
     * 预支付交易会话标识
     */
    private String prepayId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 支付链接/二维码
     */
    private String payUrl;
}
