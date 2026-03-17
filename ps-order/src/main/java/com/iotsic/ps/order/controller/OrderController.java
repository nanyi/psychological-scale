package com.iotsic.ps.order.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.order.dto.OrderCreateRequest;
import com.iotsic.ps.order.dto.OrderCreateResponse;
import com.iotsic.ps.order.dto.OrderListRequest;
import com.iotsic.ps.order.dto.OrderStatisticsResponse;
import com.iotsic.ps.order.entity.Order;
import com.iotsic.ps.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 订单控制器
 * 负责订单创建、查询、取消等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 创建订单
     * 
     * @param request 订单创建请求
     * @return 订单信息
     */
    @PostMapping("/create")
    public RestResult<OrderCreateResponse> createOrder(@RequestBody OrderCreateRequest request) {
        Order order = orderService.createOrder(request.getUserId(), request.getScaleId(), request.getOrderType());
        
        OrderCreateResponse response = new OrderCreateResponse();
        response.setOrderId(order.getId());
        response.setOrderNo(order.getOrderNo());
        
        return RestResult.success(response);
    }

    /**
     * 根据ID获取订单
     * 
     * @param id 订单ID
     * @return 订单信息
     */
    @GetMapping("/detail/{id}")
    public RestResult<Order> getOrderById(@PathVariable Long id) {
        return RestResult.success(orderService.getOrderById(id));
    }

    /**
     * 根据订单号获取订单
     * 
     * @param orderNo 订单编号
     * @return 订单信息
     */
    @GetMapping("/by-no/{orderNo}")
    public RestResult<Order> getOrderByNo(@PathVariable String orderNo) {
        return RestResult.success(orderService.getOrderByNo(orderNo));
    }

    /**
     * 取消订单
     * 
     * @param orderId 订单ID
     * @param reason 取消原因
     * @return 操作结果
     */
    @PostMapping("/cancel")
    public RestResult<Void> cancelOrder(@RequestParam(value = "orderId") Long orderId, @RequestParam(value = "reason", required = false) String reason) {
        orderService.cancelOrder(orderId, reason);
        return RestResult.success();
    }

    /**
     * 获取用户订单列表
     * 
     * @param userId 用户ID
     * @param request 分页请求
     * @return 订单分页列表
     */
    @GetMapping("/by-user/{userId}")
    public RestResult<PageResult<Order>> getUserOrders(
            @PathVariable Long userId,
            PageRequest request) {
        return RestResult.success(orderService.getUserOrders(request, userId));
    }

    /**
     * 获取订单列表
     * 
     * @param request 分页请求
     * @param orderListRequest 查询参数
     * @return 订单分页列表
     */
    @GetMapping("/list")
    public RestResult<PageResult<Order>> getOrderList(
            PageRequest request,
            OrderListRequest orderListRequest) {
        return RestResult.success(orderService.getOrderList(request, orderListRequest));
    }

    /**
     * 获取订单统计
     * 
     * @param userId 用户ID
     * @return 统计数据
     */
    @GetMapping("/statistics")
    public RestResult<OrderStatisticsResponse> getOrderStatistics(@RequestParam Long userId) {
        return RestResult.success(orderService.getOrderStatistics(userId));
    }
}
