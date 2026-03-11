package com.iotsic.ps.payment.service;

import com.iotsic.ps.payment.dto.RefundCreateResponse;
import com.iotsic.ps.payment.entity.RefundRecord;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 退款服务接口
 * 
 * @author Ryan
 * @since 2026-03-11
 */
public interface RefundService {

    /**
     * 创建退款申请
     * 
     * @param paymentId 支付记录ID
     * @param refundAmount 退款金额
     * @param refundReason 退款原因
     * @return 退款创建结果
     */
    RefundCreateResponse createRefund(Long paymentId, BigDecimal refundAmount, String refundReason);

    /**
     * 处理微信退款回调
     * 
     * @param notifyData 回调数据
     */
    void handleWxRefundNotify(Map<String, String> notifyData);

    /**
     * 处理支付宝退款回调
     * 
     * @param notifyData 回调数据
     */
    void handleAliRefundNotify(Map<String, String> notifyData);

    /**
     * 根据支付记录ID获取退款记录
     * 
     * @param paymentId 支付记录ID
     * @return 退款记录
     */
    RefundRecord getRefundByPaymentId(Long paymentId);

    /**
     * 根据退款单号获取退款记录
     * 
     * @param refundNo 退款单号
     * @return 退款记录
     */
    RefundRecord getRefundByRefundNo(String refundNo);
}
