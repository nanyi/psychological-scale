package com.iotsic.ps.payment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.payment.config.WxPayConfig;
import com.iotsic.ps.payment.dto.RefundCreateResponse;
import com.iotsic.ps.payment.entity.Payment;
import com.iotsic.ps.payment.entity.RefundRecord;
import com.iotsic.ps.payment.mapper.PaymentMapper;
import com.iotsic.ps.payment.mapper.RefundRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * 退款服务实现类
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRecordMapper refundRecordMapper;
    private final PaymentMapper paymentMapper;
    private final WxPayConfig wxPayConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefundCreateResponse createRefund(Long paymentId, BigDecimal refundAmount, String refundReason) {
        Payment payment = paymentMapper.selectById(paymentId);
        if (payment == null) {
            throw new BusinessException("支付记录不存在");
        }

        if (payment.getStatus() != 1) {
            throw new BusinessException("订单未支付，无法退款");
        }

        if (refundAmount.compareTo(payment.getPayAmount()) > 0) {
            throw new BusinessException("退款金额超过支付金额");
        }

        String refundNo = "REF" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8);

        RefundRecord refundRecord = new RefundRecord();
        refundRecord.setPaymentId(paymentId);
        refundRecord.setOrderId(payment.getOrderId());
        refundRecord.setOrderNo(payment.getOrderNo());
        refundRecord.setUserId(payment.getUserId());
        refundRecord.setRefundAmount(refundAmount);
        refundRecord.setRefundNo(refundNo);
        refundRecord.setRefundMethod(payment.getPayMethod());
        refundRecord.setRefundReason(refundReason);
        refundRecord.setStatus(0);
        refundRecord.setCreateTime(LocalDateTime.now());
        refundRecord.setUpdateTime(LocalDateTime.now());
        refundRecordMapper.insert(refundRecord);

        try {
            if ("WXPAY".equals(payment.getPayMethod())) {
                processWxRefund(refundRecord, payment);
            } else if ("ALIPAY".equals(payment.getPayMethod())) {
                processAliRefund(refundRecord, payment);
            }
        } catch (Exception e) {
            log.error("退款处理异常", e);
            refundRecord.setRefundStatus("FAILED");
            refundRecord.setRemark(e.getMessage());
            refundRecordMapper.updateById(refundRecord);
            throw new BusinessException("退款处理异常: " + e.getMessage());
        }

        RefundCreateResponse result = new RefundCreateResponse();
        result.setRefundId(refundRecord.getId());
        result.setRefundNo(refundNo);
        return result;
    }

    private void processWxRefund(RefundRecord refundRecord, Payment payment) throws Exception {
        refundRecord.setRefundStatus("PROCESSING");
        refundRecordMapper.updateById(refundRecord);
    }

    private void processAliRefund(RefundRecord refundRecord, Payment payment) throws Exception {
        refundRecord.setRefundStatus("PROCESSING");
        refundRecordMapper.updateById(refundRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleWxRefundNotify(Map<String, String> notifyData) {
        try {
            String returnCode = notifyData.get("return_code");
            String resultCode = notifyData.get("result_code");
            String refundId = notifyData.get("refund_id");
            String outRefundNo = notifyData.get("out_refund_no");

            if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
                RefundRecord refundRecord = refundRecordMapper.selectOne(
                        new LambdaQueryWrapper<RefundRecord>()
                                .eq(RefundRecord::getRefundNo, outRefundNo)
                );

                if (refundRecord != null && refundRecord.getStatus() == 0) {
                    refundRecord.setTradeRefundNo(refundId);
                    refundRecord.setRefundStatus("SUCCESS");
                    refundRecord.setRefundTime(LocalDateTime.now());
                    refundRecord.setStatus(1);
                    refundRecord.setUpdateTime(LocalDateTime.now());
                    refundRecordMapper.updateById(refundRecord);

                    log.info("微信退款回调处理成功: {}", outRefundNo);
                }
            }
        } catch (Exception e) {
            log.error("微信退款回调处理异常", e);
            throw new BusinessException("微信退款回调处理异常");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleAliRefundNotify(Map<String, String> notifyData) {
        try {
            String tradeNo = notifyData.get("trade_no");
            String outBizNo = notifyData.get("out_biz_no");
            String refundStatus = notifyData.get("refund_status");

            if ("SUCCESS".equals(refundStatus)) {
                RefundRecord refundRecord = refundRecordMapper.selectOne(
                        new LambdaQueryWrapper<RefundRecord>()
                                .eq(RefundRecord::getRefundNo, outBizNo)
                );

                if (refundRecord != null && refundRecord.getStatus() == 0) {
                    refundRecord.setTradeRefundNo(tradeNo);
                    refundRecord.setRefundStatus("SUCCESS");
                    refundRecord.setRefundTime(LocalDateTime.now());
                    refundRecord.setStatus(1);
                    refundRecord.setUpdateTime(LocalDateTime.now());
                    refundRecordMapper.updateById(refundRecord);

                    log.info("支付宝退款回调处理成功: {}", outBizNo);
                }
            }
        } catch (Exception e) {
            log.error("支付宝退款回调处理异常", e);
            throw new BusinessException("支付宝退款回调处理异常");
        }
    }

    @Override
    public RefundRecord getRefundByPaymentId(Long paymentId) {
        return refundRecordMapper.selectOne(
                new LambdaQueryWrapper<RefundRecord>()
                        .eq(RefundRecord::getPaymentId, paymentId)
                        .orderByDesc(RefundRecord::getCreateTime)
                        .last("LIMIT 1")
        );
    }

    @Override
    public RefundRecord getRefundByRefundNo(String refundNo) {
        return refundRecordMapper.selectOne(
                new LambdaQueryWrapper<RefundRecord>()
                        .eq(RefundRecord::getRefundNo, refundNo)
        );
    }
}
