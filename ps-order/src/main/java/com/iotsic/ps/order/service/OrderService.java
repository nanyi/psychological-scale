package com.iotsic.ps.order.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.order.dto.OrderListRequest;
import com.iotsic.ps.order.dto.OrderStatisticsResponse;
import com.iotsic.ps.order.entity.Order;

import java.util.Map;

public interface OrderService {

    Order createOrder(Long userId, Long scaleId, Integer orderType);

    Order getOrderById(Long id);

    Order getOrderByNo(String orderNo);

    void cancelOrder(Long orderId, String reason);

    PageResult<Order> getUserOrders(PageRequest request, Long userId);

    PageResult<Order> getOrderList(PageRequest request, OrderListRequest params);

    void expireOrder(Long orderId);

    OrderStatisticsResponse getOrderStatistics(Long userId);
}
