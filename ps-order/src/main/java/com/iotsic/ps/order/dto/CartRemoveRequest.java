package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 购物车移除请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class CartRemoveRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 量表ID
     */
    private Long scaleId;
}
