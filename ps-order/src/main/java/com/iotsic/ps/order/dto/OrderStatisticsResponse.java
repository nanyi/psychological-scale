package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 订单统计响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class OrderStatisticsResponse {

    /**
     * 订单总数
     */
    private Long totalCount;

    /**
     * 待支付数量
     */
    private Long pendingPaymentCount;

    /**
     * 已支付数量
     */
    private Long paidCount;

    /**
     * 已取消数量
     */
    private Long cancelledCount;

    /**
     * 总金额
     */
    private java.math.BigDecimal totalAmount;

    /**
     * 支付金额
     */
    private java.math.BigDecimal paidAmount;
}
