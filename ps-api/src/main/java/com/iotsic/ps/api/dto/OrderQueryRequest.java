package com.iotsic.ps.api.dto;

import lombok.Data;

/**
 * 订单查询请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class OrderQueryRequest {

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
}
