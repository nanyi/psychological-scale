package com.iotsic.ps.payment.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.payment.config.WxPayConfig;
import com.iotsic.ps.payment.entity.Payment;
import com.iotsic.ps.payment.mapper.PaymentMapper;
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
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final WxPayConfig wxPayConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createWxPayOrder(Long orderId, String orderNo, BigDecimal amount, String description) {
        try {
            WXPay wxPay = new WXPay(wxPayConfig);

            Map<String, String> params = new HashMap<>();
            params.put("body", description);
            params.put("out_trade_no", orderNo);
            params.put("total_fee", amount.multiply(new BigDecimal("100")).intValue() + "");
            params.put("spbill_create_ip", "127.0.0.1");
            params.put("notify_url", wxPayConfig.getNotifyUrl());
            params.put("trade_type", "NATIVE");

            Map<String, String> response = wxPay.unifiedOrder(params);

            if ("SUCCESS".equals(response.get("return_code")) && "SUCCESS".equals(response.get("result_code"))) {
                String codeUrl = response.get("code_url");

                Payment payment = new Payment();
                payment.setOrderId(orderId);
                payment.setOrderNo(orderNo);
                payment.setPayAmount(amount);
                payment.setPayMethod("WXPAY");
                payment.setStatus(0);
                payment.setCreateTime(LocalDateTime.now());
                payment.setUpdateTime(LocalDateTime.now());
                paymentMapper.insert(payment);

                Map<String, Object> result = new HashMap<>();
                result.put("paymentId", payment.getId());
                result.put("codeUrl", codeUrl);
                result.put("orderNo", orderNo);
                return result;
            } else {
                log.error("微信支付下单失败: {}", response);
                throw new BusinessException("微信支付下单失败: " + response.get("return_msg"));
            }
        } catch (Exception e) {
            log.error("微信支付创建订单异常", e);
            throw new BusinessException("微信支付创建订单异常: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createAliPayOrder(Long orderId, String orderNo, BigDecimal amount, String description) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleWxPayNotify(Map<String, String> notifyData) {
        try {
            String returnCode = notifyData.get("return_code");
            String resultCode = notifyData.get("result_code");
            String transactionId = notifyData.get("transaction_id");
            String outTradeNo = notifyData.get("out_trade_no");

            if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
                Payment payment = paymentMapper.selectOne(
                        new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Payment>()
                                .eq(Payment::getOrderNo, outTradeNo)
                );

                if (payment != null && payment.getStatus() == 0) {
                    payment.setTransactionId(transactionId);
                    payment.setTradeNo(transactionId);
                    payment.setTradeStatus("SUCCESS");
                    payment.setPayTime(LocalDateTime.now());
                    payment.setStatus(1);
                    payment.setNotifyData(notifyData.toString());
                    payment.setUpdateTime(LocalDateTime.now());
                    paymentMapper.updateById(payment);

                    log.info("微信支付回调处理成功: {}", outTradeNo);
                }
            } else {
                log.error("微信支付回调失败: {}", notifyData);
            }
        } catch (Exception e) {
            log.error("微信支付回调处理异常", e);
            throw new BusinessException("微信支付回调处理异常");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleAliPayNotify(Map<String, String> notifyData) {
        try {
            String tradeStatus = notifyData.get("trade_status");
            String tradeNo = notifyData.get("trade_no");
            String outTradeNo = notifyData.get("out_trade_no");

            if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                Payment payment = paymentMapper.selectOne(
                        new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Payment>()
                                .eq(Payment::getOrderNo, outTradeNo)
                );

                if (payment != null && payment.getStatus() == 0) {
                    payment.setTransactionId(tradeNo);
                    payment.setTradeNo(tradeNo);
                    payment.setTradeStatus(tradeStatus);
                    payment.setPayTime(LocalDateTime.now());
                    payment.setStatus(1);
                    payment.setNotifyData(notifyData.toString());
                    payment.setUpdateTime(LocalDateTime.now());
                    paymentMapper.updateById(payment);

                    log.info("支付宝回调处理成功: {}", outTradeNo);
                }
            }
        } catch (Exception e) {
            log.error("支付宝回调处理异常", e);
            throw new BusinessException("支付宝回调处理异常");
        }
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        return paymentMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Payment>()
                        .eq(Payment::getOrderId, orderId)
                        .orderByDesc(Payment::getCreateTime)
                        .last("LIMIT 1")
        );
    }

    @Override
    public Payment getPaymentByTradeNo(String tradeNo) {
        return paymentMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Payment>()
                        .eq(Payment::getTradeNo, tradeNo)
        );
    }
}
