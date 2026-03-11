package com.iotsic.ps.order.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.order.entity.Order;
import com.iotsic.ps.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public RestResult<Order> createOrder(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        Integer orderType = (Integer) params.getOrDefault("orderType", 1);

        Order order = orderService.createOrder(userId, scaleId, orderType);
        return RestResult.success("订单创建成功", order);
    }

    @GetMapping("/{id}")
    public RestResult<Order> getOrderById(@PathVariable Long id) {
        return RestResult.success(orderService.getOrderById(id));
    }

    @GetMapping("/no/{orderNo}")
    public RestResult<Order> getOrderByNo(@PathVariable String orderNo) {
        return RestResult.success(orderService.getOrderByNo(orderNo));
    }

    @PostMapping("/cancel")
    public RestResult<Void> cancelOrder(@RequestBody Map<String, Object> params) {
        Long orderId = Long.valueOf(params.get("orderId").toString());
        String reason = (String) params.getOrDefault("reason", "用户取消");

        orderService.cancelOrder(orderId, reason);
        return RestResult.success();
    }

    @GetMapping("/user/{userId}")
    public RestResult<PageResult<Order>> getUserOrders(
            @PathVariable Long userId,
            PageRequest request) {
        return RestResult.success(orderService.getUserOrders(request, userId));
    }

    @GetMapping("/list")
    public RestResult<PageResult<Order>> getOrderList(
            PageRequest request,
            @RequestParam(required = false) Map<String, Object> params) {
        return RestResult.success(orderService.getOrderList(request, params));
    }

    @GetMapping("/statistics")
    public RestResult<Map<String, Object>> getOrderStatistics(@RequestParam Long userId) {
        return RestResult.success(orderService.getOrderStatistics(userId));
    }
}
