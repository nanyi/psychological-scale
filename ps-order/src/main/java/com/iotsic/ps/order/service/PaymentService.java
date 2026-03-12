package com.iotsic.ps.order.service;

import com.iotsic.ps.order.dto.PaymentResponse;
import com.iotsic.ps.order.entity.Order;

import java.util.Map;

/**
 * 支付服务接口
 * 负责支付订单创建、回调处理、状态查询、取消等业务操作
 *
 * @author Ryan
 * @since 2026-03-12
 */
public interface PaymentService {

    /**
     * 创建微信支付订单
     *
     * @param orderId 订单ID
     * @param returnUrl 返回URL
     * @return 支付响应
     */
    PaymentResponse createWechatPayOrder(Long orderId, String returnUrl);

    /**
     * 创建支付宝订单
     *
     * @param orderId 订单ID
     * @param returnUrl 返回URL
     * @return 支付响应
     */
    PaymentResponse createAlipayOrder(Long orderId, String returnUrl);

    /**
     * 处理微信支付回调
     *
     * @param params 回调参数
     */
    void handleWechatPayCallback(Map<String, Object> params);

    /**
     * 处理支付宝回调
     *
     * @param params 回调参数
     */
    void handleAlipayCallback(Map<String, Object> params);

    /**
     * 查询支付状态
     *
     * @param orderId 订单ID
     * @return 支付状态结果
     */
    Map<String, Object> queryPaymentStatus(Long orderId);

    /**
     * 取消支付
     *
     * @param orderId 订单ID
     */
    void cancelPayment(Long orderId);
}
