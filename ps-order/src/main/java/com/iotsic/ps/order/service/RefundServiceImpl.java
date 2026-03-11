package com.iotsic.ps.order.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.constant.BusinessConstant;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.utils.EncryptUtils;
import com.iotsic.ps.order.entity.Order;
import com.iotsic.ps.order.entity.Refund;
import com.iotsic.ps.order.mapper.OrderMapper;
import com.iotsic.ps.order.mapper.RefundMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundMapper refundMapper;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Refund createRefund(Long orderId, String reason) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getDeleted() == 1) {
            throw BusinessException.of("ORDER_NOT_FOUND", "订单不存在");
        }

        if (order.getOrderStatus() != 1) {
            throw BusinessException.of("REFUND_NOT_ALLOWED", "只有已支付的订单才能退款");
        }

        LambdaQueryWrapper<Refund> existWrapper = new LambdaQueryWrapper<>();
        existWrapper.eq(Refund::getOrderId, orderId);
        Refund existRefund = refundMapper.selectOne(existWrapper);
        if (existRefund != null) {
            throw BusinessException.of("REFUND_EXIST", "该订单已存在退款申请");
        }

        long daysSincePay = 0;
        if (order.getPayTime() != null) {
            daysSincePay = java.time.Duration.between(order.getPayTime(), LocalDateTime.now()).toDays();
        }
        if (daysSincePay > BusinessConstant.REFUND_DAYS) {
            throw BusinessException.of("REFUND_EXCEED", "已超过退款时限（" + BusinessConstant.REFUND_DAYS + "天）");
        }

        Refund refund = new Refund();
        refund.setOrderId(orderId);
        refund.setRefundNo(EncryptUtils.generateUUID().substring(0, 16));
        refund.setUserId(order.getUserId());
        refund.setRefundAmount(order.getActualAmount());
        refund.setRefundStatus(0);
        refund.setRefundReason(reason);
        refund.setRefundMethod(order.getPayMethod());
        refund.setCreateTime(LocalDateTime.now());
        refund.setUpdateTime(LocalDateTime.now());

        refundMapper.insert(refund);

        order.setOrderStatus(3);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);

        log.info("创建退款申请: orderId={}, refundNo={}, amount={}", orderId, refund.getRefundNo(), refund.getRefundAmount());

        return refund;
    }

    @Override
    public Refund getRefundById(Long id) {
        Refund refund = refundMapper.selectById(id);
        if (refund == null || refund.getDeleted() == 1) {
            throw BusinessException.of("REFUND_NOT_FOUND", "退款记录不存在");
        }
        return refund;
    }

    @Override
    public Refund getRefundByOrderId(Long orderId) {
        LambdaQueryWrapper<Refund> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Refund::getOrderId, orderId);
        return refundMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveRefund(Long refundId) {
        Refund refund = getRefundById(refundId);

        if (refund.getRefundStatus() != 0) {
            throw BusinessException.of("REFUND_STATUS_ERROR", "退款状态不允许审批");
        }

        refund.setRefundStatus(1);
        refund.setRefundTime(LocalDateTime.now());
        refund.setUpdateTime(LocalDateTime.now());
        refundMapper.updateById(refund);

        Order order = orderMapper.selectById(refund.getOrderId());
        if (order != null) {
            order.setOrderStatus(3);
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
        }

        log.info("退款审批通过: refundId={}, refundNo={}", refundId, refund.getRefundNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectRefund(Long refundId, String reason) {
        Refund refund = getRefundById(refundId);

        if (refund.getRefundStatus() != 0) {
            throw BusinessException.of("REFUND_STATUS_ERROR", "退款状态不允许拒绝");
        }

        refund.setRefundStatus(2);
        refund.setRejectReason(reason);
        refund.setUpdateTime(LocalDateTime.now());
        refundMapper.updateById(refund);

        Order order = orderMapper.selectById(refund.getOrderId());
        if (order != null) {
            order.setOrderStatus(1);
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
        }

        log.info("退款申请被拒绝: refundId={}, reason={}", refundId, reason);
    }

    @Override
    public PageResult<Refund> getRefundList(PageRequest request, Map<String, Object> params) {
        Page<Refund> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<Refund> wrapper = new LambdaQueryWrapper<>();

        if (params != null) {
            if (params.containsKey("userId") && params.get("userId") != null) {
                wrapper.eq(Refund::getUserId, params.get("userId"));
            }
            if (params.containsKey("refundStatus") && params.get("refundStatus") != null) {
                wrapper.eq(Refund::getRefundStatus, params.get("refundStatus"));
            }
        }

        wrapper.orderByDesc(Refund::getCreateTime);
        IPage<Refund> result = refundMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), request.getPageNum(), request.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processRefundCallback(Map<String, Object> params) {
        String refundNo = (String) params.get("refundNo");
        String transactionId = (String) params.get("transactionId");

        LambdaQueryWrapper<Refund> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Refund::getRefundNo, refundNo);
        Refund refund = refundMapper.selectOne(wrapper);

        if (refund == null) {
            log.error("退款回调订单不存在: refundNo={}", refundNo);
            return;
        }

        if (refund.getRefundStatus() != 1) {
            log.info("退款已完成: refundNo={}, status={}", refundNo, refund.getRefundStatus());
            return;
        }

        refund.setTransactionId(transactionId);
        refund.setRefundStatus(3);
        refund.setUpdateTime(LocalDateTime.now());
        refundMapper.updateById(refund);

        log.info("退款成功: refundNo={}, transactionId={}", refundNo, transactionId);
    }
}
