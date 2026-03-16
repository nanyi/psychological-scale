package com.iotsic.ps.order.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.order.entity.Cart;
import com.iotsic.ps.order.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Cart addToCart(Long userId, Long scaleId, Integer quantity) {
        Cart existCart = getCartItem(userId, scaleId);
        
        if (existCart != null) {
            existCart.setQuantity(existCart.getQuantity() + quantity);
            existCart.setTotalAmount(existCart.getPrice().multiply(BigDecimal.valueOf(existCart.getQuantity())));
            existCart.setUpdateTime(LocalDateTime.now());
            cartMapper.updateById(existCart);
            return existCart;
        }

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setScaleId(scaleId);
        cart.setQuantity(quantity);
        cart.setCreateTime(LocalDateTime.now());
        cart.setUpdateTime(LocalDateTime.now());

        cartMapper.insert(cart);
        return cart;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFromCart(Long userId, Long scaleId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getScaleId, scaleId);
        cartMapper.delete(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCartQuantity(Long userId, Long scaleId, Integer quantity) {
        Cart cart = getCartItem(userId, scaleId);
        if (cart == null) {
            throw BusinessException.of(ErrorCodeEnum.CART_ITEM_NOT_FOUND.getCode(), "购物车商品不存在");
        }

        if (quantity <= 0) {
            removeFromCart(userId, scaleId);
            return;
        }

        cart.setQuantity(quantity);
        cart.setTotalAmount(cart.getPrice().multiply(BigDecimal.valueOf(quantity)));
        cart.setUpdateTime(LocalDateTime.now());
        cartMapper.updateById(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearCart(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        cartMapper.delete(wrapper);
    }

    @Override
    public List<Cart> getUserCart(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId)
                .orderByDesc(Cart::getCreateTime);
        return cartMapper.selectList(wrapper);
    }

    @Override
    public Cart getCartItem(Long userId, Long scaleId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getScaleId, scaleId);
        return cartMapper.selectOne(wrapper);
    }
}
