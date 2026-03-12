package com.iotsic.ps.order.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.constant.BusinessConstant;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.utils.EncryptUtils;
import com.iotsic.ps.order.dto.OrderListRequest;
import com.iotsic.ps.order.dto.OrderStatisticsResponse;
import com.iotsic.ps.order.entity.Order;
import com.iotsic.ps.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Long userId, Long scaleId, Integer orderType) {
        BigDecimal price = BigDecimal.valueOf(99.00);

        Order order = new Order();
        order.setOrderNo(EncryptUtils.generateUUID().substring(0, 16));
        order.setUserId(userId);
        order.setTotalAmount(price);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setActualAmount(price);
        order.setOrderType(orderType);
        order.setOrderStatus(0);
        order.setExpireTime(LocalDateTime.now().plusMinutes(BusinessConstant.ORDER_EXPIRE_MINUTES));
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());

        orderMapper.insert(order);
        return order;
    }

    @Override
    public Order getOrderById(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null || order.getDeleted() == 1) {
            throw BusinessException.of("ORDER_NOT_FOUND", "订单不存在");
        }
        return order;
    }

    @Override
    public Order getOrderByNo(String orderNo) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getOrderNo, orderNo);
        Order order = orderMapper.selectOne(wrapper);
        if (order == null || order.getDeleted() == 1) {
            throw BusinessException.of("ORDER_NOT_FOUND", "订单不存在");
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long orderId, String reason) {
        Order order = getOrderById(orderId);
        
        if (order.getOrderStatus() != 0) {
            throw BusinessException.of("ORDER_CANNOT_CANCEL", "订单状态不允许取消");
        }

        order.setOrderStatus(2);
        order.setCancelReason(reason);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    @Override
    public PageResult<Order> getUserOrders(PageRequest request, Long userId) {
        Page<Order> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId)
                .orderByDesc(Order::getCreateTime);
        IPage<Order> result = orderMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), request.getPageNum(), request.getPageSize());
    }

    @Override
    public PageResult<Order> getOrderList(PageRequest request, OrderListRequest params) {
        Page<Order> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        
        if (params != null) {
            if (params.getUserId() != null) {
                wrapper.eq(Order::getUserId, params.getUserId());
            }
            if (params.getStatus() != null) {
                wrapper.eq(Order::getOrderStatus, params.getStatus());
            }
            if (params.getPayType() != null) {
                wrapper.eq(Order::getOrderType, params.getPayType());
            }
        }
        
        wrapper.orderByDesc(Order::getCreateTime);
        IPage<Order> result = orderMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), request.getPageNum(), request.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void expireOrder(Long orderId) {
        Order order = getOrderById(orderId);
        
        if (order.getOrderStatus() == 0 && order.getExpireTime().isBefore(LocalDateTime.now())) {
            order.setOrderStatus(4);
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
        }
    }

    @Override
    public OrderStatisticsResponse getOrderStatistics(Long userId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        
        java.util.List<Order> orders = orderMapper.selectList(wrapper);
        
        long totalOrders = orders.size();
        long paidOrders = orders.stream()
                .filter(o -> o.getOrderStatus() != null && o.getOrderStatus() == 1)
                .count();
        long pendingOrders = orders.stream()
                .filter(o -> o.getOrderStatus() != null && o.getOrderStatus() == 0)
                .count();
        
        BigDecimal totalAmount = orders.stream()
                .filter(o -> o.getActualAmount() != null)
                .map(Order::getActualAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal paidAmount = orders.stream()
                .filter(o -> o.getOrderStatus() != null && o.getOrderStatus() == 1)
                .filter(o -> o.getActualAmount() != null)
                .map(Order::getActualAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        OrderStatisticsResponse response = new OrderStatisticsResponse();
        response.setTotalCount(totalOrders);
        response.setPendingPaymentCount(pendingOrders);
        response.setPaidCount(paidOrders);
        response.setTotalAmount(totalAmount);
        response.setPaidAmount(paidAmount);
        
        return response;
    }
}
