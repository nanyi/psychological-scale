package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 购物车更新请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class CartUpdateRequest {

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
    private Integer quantity;
}
