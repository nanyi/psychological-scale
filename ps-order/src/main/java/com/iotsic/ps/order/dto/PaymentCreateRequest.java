package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 支付创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class PaymentCreateRequest {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 回调地址
     */
    private String returnUrl;
}
