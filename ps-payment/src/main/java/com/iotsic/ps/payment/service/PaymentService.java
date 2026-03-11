package com.iotsic.ps.payment.service;

import com.iotsic.ps.payment.entity.Payment;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentService {

    Map<String, Object> createWxPayOrder(Long orderId, String orderNo, BigDecimal amount, String description);

    Map<String, Object> createAliPayOrder(Long orderId, String orderNo, BigDecimal amount, String description);

    void handleWxPayNotify(Map<String, String> notifyData);

    void handleAliPayNotify(Map<String, String> notifyData);

    Payment getPaymentByOrderId(Long orderId);

    Payment getPaymentByTradeNo(String tradeNo);
}
