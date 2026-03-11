package com.iotsic.ps.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class OrderCreateRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;
}
