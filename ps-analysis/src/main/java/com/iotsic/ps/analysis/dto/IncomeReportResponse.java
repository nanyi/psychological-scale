package com.iotsic.ps.analysis.dto;

import lombok.Data;

/**
 * 收入报表响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class IncomeReportResponse {

    /**
     * 总收入
     */
    private java.math.BigDecimal totalIncome;

    /**
     * 微信支付收入
     */
    private java.math.BigDecimal wechatIncome;

    /**
     * 支付宝收入
     */
    private java.math.BigDecimal alipayIncome;

    /**
     * 订单数量
     */
    private Long orderCount;

    /**
     * 退款金额
     */
    private java.math.BigDecimal refundAmount;

    /**
     * 净收入
     */
    private java.math.BigDecimal netIncome;
}
