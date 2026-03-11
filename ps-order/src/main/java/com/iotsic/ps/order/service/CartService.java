package com.iotsic.ps.order.service;

import com.iotsic.ps.order.entity.Cart;

import java.util.List;

public interface CartService {

    Cart addToCart(Long userId, Long scaleId, Integer quantity);

    void removeFromCart(Long userId, Long scaleId);

    void updateCartQuantity(Long userId, Long scaleId, Integer quantity);

    void clearCart(Long userId);

    List<Cart> getUserCart(Long userId);

    Cart getCartItem(Long userId, Long scaleId);
}
