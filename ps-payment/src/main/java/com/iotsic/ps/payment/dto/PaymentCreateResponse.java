package com.iotsic.ps.payment.dto;

import lombok.Data;

/**
 * 支付创建响应DTO
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@Data
public class PaymentCreateResponse {

    /**
     * 支付记录ID
     */
    private Long paymentId;

    /**
     * 支付二维码链接（微信NATIVE支付）
     */
    private String codeUrl;

    /**
     * 订单编号
     */
    private String orderNo;
}
