package com.iotsic.ps.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 退款创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class RefundCreateRequest {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单项ID列表
     */
    private List<Long> orderItemIds;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因描述
     */
    private String refundReason;
}
