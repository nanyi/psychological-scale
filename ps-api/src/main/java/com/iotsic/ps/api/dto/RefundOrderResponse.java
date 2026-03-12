package com.iotsic.ps.api.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 退款订单响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class RefundOrderResponse {

    /**
     * 退款单号
     */
    private String refundNo;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款状态 (0-处理中, 1-成功, 2-失败)
     */
    private Integer status;
}