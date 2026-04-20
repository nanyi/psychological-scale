package com.iotsic.ps.order.controller;

import com.iotsic.ps.order.dto.CartAddRequest;
import com.iotsic.ps.order.dto.CartRemoveRequest;
import com.iotsic.ps.order.dto.CartUpdateRequest;
import com.iotsic.ps.order.entity.Cart;
import com.iotsic.ps.order.service.CartService;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 购物车控制器
 * 负责购物车添加、移除、更新、查询等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 添加商品到购物车
     * 
     * @param request 购物车添加请求
     * @return 购物车记录
     */
    @PostMapping("/add")
    public RestResult<Cart> addToCart(@RequestBody CartAddRequest request) {
        Cart cart = cartService.addToCart(
                request.getUserId(),
                request.getScaleId(),
                request.getQuantity() != null ? request.getQuantity() : 1
        );
        return RestResult.success("已添加到购物车", cart);
    }

    /**
     * 从购物车移除商品
     * 
     * @param request 购物车移除请求
     * @return 操作结果
     */
    @PostMapping("/remove")
    public RestResult<Void> removeFromCart(@RequestBody CartRemoveRequest request) {
        cartService.removeFromCart(request.getUserId(), request.getScaleId());
        return RestResult.success();
    }

    /**
     * 更新购物车商品数量
     * 
     * @param request 购物车更新请求
     * @return 操作结果
     */
    @PostMapping("/update")
    public RestResult<Void> updateCartQuantity(@RequestBody CartUpdateRequest request) {
        cartService.updateCartQuantity(
                request.getUserId(),
                request.getScaleId(),
                request.getQuantity()
        );
        return RestResult.success();
    }

    /**
     * 清空购物车
     * 
     * @param userId 用户ID
     * @return 操作结果
     */
    @PostMapping("/clear")
    public RestResult<Void> clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return RestResult.success();
    }

    /**
     * 获取用户购物车列表
     * 
     * @param userId 用户ID
     * @return 购物车列表
     */
    @GetMapping("/list")
    public RestResult<List<Cart>> getUserCart(@RequestParam Long userId) {
        return RestResult.success(cartService.getUserCart(userId));
    }
}
