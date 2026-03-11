package com.iotsic.ps.api.dto;

import lombok.Data;

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
     * 量表ID
     */
    private Long scaleId;

    /**
     * 订单类型
     */
    private Integer orderType;
}
