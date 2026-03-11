package com.iotsic.ps.payment.service;

import com.iotsic.ps.payment.entity.RefundRecord;

import java.math.BigDecimal;
import java.util.Map;

public interface RefundService {

    Map<String, Object> createRefund(Long paymentId, BigDecimal refundAmount, String refundReason);

    void handleWxRefundNotify(Map<String, String> notifyData);

    void handleAliRefundNotify(Map<String, String> notifyData);

    RefundRecord getRefundByPaymentId(Long paymentId);

    RefundRecord getRefundByRefundNo(String refundNo);
}
