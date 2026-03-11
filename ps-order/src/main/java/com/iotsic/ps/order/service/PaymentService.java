package com.iotsic.ps.order.service;

import com.iotsic.ps.order.entity.Order;

import java.util.Map;

public interface PaymentService {

    Map<String, Object> createWechatPayOrder(Long orderId, String returnUrl);

    Map<String, Object> createAlipayOrder(Long orderId, String returnUrl);

    void handleWechatPayCallback(Map<String, Object> params);

    void handleAlipayCallback(Map<String, Object> params);

    Map<String, Object> queryPaymentStatus(Long orderId);

    void cancelPayment(Long orderId);
}
