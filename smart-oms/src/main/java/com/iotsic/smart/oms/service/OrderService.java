package com.iotsic.smart.oms.service;

import com.iotsic.smart.oms.dto.OrderListRequest;
import com.iotsic.smart.oms.dto.OrderStatisticsResponse;
import com.iotsic.smart.oms.entity.Order;
import com.iotsic.smart.framework.common.dto.request.PageRequest;
import com.iotsic.smart.framework.common.dto.response.PageResult;

/**
 * 订单服务接口
 * 负责订单的创建、查询、取消、统计等业务操作
 *
 * @author Ryan
 * @since 2026-03-12
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param userId 用户ID
     * @param scaleId 量表ID
     * @param orderType 订单类型
     * @return 创建的订单实体
     */
    Order createOrder(Long userId, Long scaleId, Integer orderType);

    /**
     * 根据ID获取订单
     *
     * @param id 订单ID
     * @return 订单实体
     */
    Order getOrderById(Long id);

    /**
     * 根据订单号获取订单
     *
     * @param orderNo 订单号
     * @return 订单实体
     */
    Order getOrderByNo(String orderNo);

    /**
     * 取消订单
     *
     * @param orderId 订单ID
     * @param reason 取消原因
     */
    void cancelOrder(Long orderId, String reason);

    /**
     * 获取用户订单列表
     *
     * @param request 分页请求
     * @param userId 用户ID
     * @return 订单分页结果
     */
    PageResult<Order> getUserOrders(PageRequest request, Long userId);

    /**
     * 获取订单列表
     *
     * @param request 分页请求
     * @param params 查询参数
     * @return 订单分页结果
     */
    PageResult<Order> getOrderList(PageRequest request, OrderListRequest params);

    /**
     * 过期订单
     *
     * @param orderId 订单ID
     */
    void expireOrder(Long orderId);

    /**
     * 获取订单统计
     *
     * @param userId 用户ID
     * @return 订单统计结果
     */
    OrderStatisticsResponse getOrderStatistics(Long userId);
}
