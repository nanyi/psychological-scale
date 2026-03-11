package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 订单创建响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class OrderCreateResponse {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;
}
