package com.iotsic.ps.order.service;

import com.iotsic.ps.order.entity.Cart;

import java.util.List;

/**
 * 购物车服务接口
 * 负责购物车的添加、移除、更新、查询等业务操作
 *
 * @author Ryan
 * @since 2026-03-12
 */
public interface CartService {

    /**
     * 添加到购物车
     *
     * @param userId 用户ID
     * @param scaleId 量表ID
     * @param quantity 数量
     * @return 购物车项
     */
    Cart addToCart(Long userId, Long scaleId, Integer quantity);

    /**
     * 从购物车移除
     *
     * @param userId 用户ID
     * @param scaleId 量表ID
     */
    void removeFromCart(Long userId, Long scaleId);

    /**
     * 更新购物车数量
     *
     * @param userId 用户ID
     * @param scaleId 量表ID
     * @param quantity 数量
     */
    void updateCartQuantity(Long userId, Long scaleId, Integer quantity);

    /**
     * 清空购物车
     *
     * @param userId 用户ID
     */
    void clearCart(Long userId);

    /**
     * 获取用户购物车
     *
     * @param userId 用户ID
     * @return 购物车列表
     */
    List<Cart> getUserCart(Long userId);

    /**
     * 获取购物车项
     *
     * @param userId 用户ID
     * @param scaleId 量表ID
     * @return 购物车项
     */
    Cart getCartItem(Long userId, Long scaleId);
}
