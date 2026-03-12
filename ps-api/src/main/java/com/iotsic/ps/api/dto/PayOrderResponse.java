package com.iotsic.ps.api.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付订单响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class PayOrderResponse {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 微信预支付ID
     */
    private String prepayId;

    /**
     * 支付跳转URL
     */
    private String payUrl;

    /**
     * 支付金额
     */
    private BigDecimal amount;
}