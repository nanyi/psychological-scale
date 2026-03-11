package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 支付取消请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class PaymentCancelRequest {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 取消原因
     */
    private String reason;
}
