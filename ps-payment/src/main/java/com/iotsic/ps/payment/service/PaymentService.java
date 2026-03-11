package com.iotsic.ps.payment.service;

import com.iotsic.ps.payment.dto.PaymentCreateResponse;
import com.iotsic.ps.payment.entity.Payment;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付服务接口
 * 
 * @author Ryan
 * @since 2026-03-11
 */
public interface PaymentService {

    /**
     * 创建微信支付订单
     * 
     * @param orderId 订单ID
     * @param orderNo 订单编号
     * @param amount 支付金额
     * @param description 订单描述
     * @return 支付创建结果
     */
    PaymentCreateResponse createWxPayOrder(Long orderId, String orderNo, BigDecimal amount, String description);

    /**
     * 创建支付宝订单
     * 
     * @param orderId 订单ID
     * @param orderNo 订单编号
     * @param amount 支付金额
     * @param description 订单描述
     * @return 支付创建结果
     */
    PaymentCreateResponse createAliPayOrder(Long orderId, String orderNo, BigDecimal amount, String description);

    /**
     * 处理微信支付回调
     * 
     * @param notifyData 回调数据
     */
    void handleWxPayNotify(Map<String, String> notifyData);

    /**
     * 处理支付宝回调
     * 
     * @param notifyData 回调数据
     */
    void handleAliPayNotify(Map<String, String> notifyData);

    /**
     * 根据订单ID获取支付记录
     * 
     * @param orderId 订单ID
     * @return 支付记录
     */
    Payment getPaymentByOrderId(Long orderId);

    /**
     * 根据交易号获取支付记录
     * 
     * @param tradeNo 交易号
     * @return 支付记录
     */
    Payment getPaymentByTradeNo(String tradeNo);
}
