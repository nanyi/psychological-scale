package com.iotsic.ps.order.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.order.entity.Cart;
import com.iotsic.ps.order.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public RestResult<Cart> addToCart(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        Integer quantity = (Integer) params.getOrDefault("quantity", 1);

        Cart cart = cartService.addToCart(userId, scaleId, quantity);
        return RestResult.success("已添加到购物车", cart);
    }

    @PostMapping("/remove")
    public RestResult<Void> removeFromCart(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long scaleId = Long.valueOf(params.get("scaleId").toString());

        cartService.removeFromCart(userId, scaleId);
        return RestResult.success();
    }

    @PostMapping("/update")
    public RestResult<Void> updateCartQuantity(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        Integer quantity = Integer.valueOf(params.get("quantity").toString());

        cartService.updateCartQuantity(userId, scaleId, quantity);
        return RestResult.success();
    }

    @PostMapping("/clear")
    public RestResult<Void> clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return RestResult.success();
    }

    @GetMapping("/list")
    public RestResult<List<Cart>> getUserCart(@RequestParam Long userId) {
        return RestResult.success(cartService.getUserCart(userId));
    }
}
