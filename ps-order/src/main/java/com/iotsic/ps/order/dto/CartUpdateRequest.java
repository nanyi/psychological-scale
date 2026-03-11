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
     * 购物车ID
     */
    private Long cartId;

    /**
     * 数量
     */
    private Integer quantity;
}
