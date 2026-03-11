package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 订单列表查询请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class OrderListRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 关键词（订单号/用户名）
     */
    private String keyword;
}
