package com.iotsic.ps.api.dto;

import lombok.Data;

/**
 * 支付订单请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class PayOrderRequest {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 支付方式 (如 wechat, alipay)
     */
    private String payMethod;

    /**
     * 支付成功后的回调地址
     */
    private String returnUrl;
}