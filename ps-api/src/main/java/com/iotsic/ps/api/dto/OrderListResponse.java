package com.iotsic.ps.api.dto;

import lombok.Data;

import java.util.List;

/**
 * 订单列表响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class OrderListResponse {

    /**
     * 订单列表
     */
    private List<OrderResponse> orders;

    /**
     * 总数
     */
    private Long total;
}