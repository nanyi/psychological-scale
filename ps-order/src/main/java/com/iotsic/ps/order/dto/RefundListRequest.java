package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 退款列表查询请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class RefundListRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 退款状态
     */
    private Integer status;

    /**
     * 订单编号
     */
    private String orderNo;
}
