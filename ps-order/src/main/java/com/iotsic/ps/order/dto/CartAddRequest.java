package com.iotsic.ps.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车添加请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class CartAddRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 数量
     */
    private Integer quantity = 1;
}
