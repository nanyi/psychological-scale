package com.iotsic.ps.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class OrderResponse {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 量表名称
     */
    private String scaleName;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 实际支付金额
     */
    private BigDecimal actualAmount;

    /**
     * 订单状态 (0-待支付, 1-已支付, 2-已取消, 3-已退款)
     */
    private Integer orderStatus;

    /**
     * 订单类型 (如 VIP会员, 单次购买)
     */
    private String orderType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}