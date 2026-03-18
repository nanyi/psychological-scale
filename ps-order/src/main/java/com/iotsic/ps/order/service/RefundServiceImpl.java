package com.iotsic.ps.order.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.constant.BusinessConstant;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.order.dto.RefundCreateResponse;
import com.iotsic.ps.order.dto.RefundListRequest;
import com.iotsic.ps.order.entity.Order;
import com.iotsic.ps.order.entity.OrderItem;
import com.iotsic.ps.order.entity.Refund;
import com.iotsic.ps.order.mapper.OrderItemMapper;
import com.iotsic.ps.order.mapper.OrderMapper;
import com.iotsic.ps.order.mapper.RefundMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundMapper refundMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefundCreateResponse createRefund(String orderNo, List<Long> orderItemIds, String reason) {
        Order order = orderMapper.selectOne(
            new LambdaQueryWrapper<Order>().eq(Order::getOrderNo, orderNo)
        );
        
        if (order == null || order.getDeleted() == 1) {
            throw BusinessException.of(ErrorCodeEnum.ORDER_NOT_FOUND.getCode(), "订单不存在");
        }

        if (order.getOrderStatus() != 1) {
            throw BusinessException.of(ErrorCodeEnum.REFUND_NOT_ALLOWED.getCode(), "只有已支付的订单才能退款");
        }

        if (orderItemIds == null || orderItemIds.isEmpty()) {
            throw BusinessException.of(ErrorCodeEnum.REFUND_ITEM_EMPTY.getCode(), "请选择要退款的量表");
        }

        BigDecimal refundAmount = BigDecimal.ZERO;
        for (Long itemId : orderItemIds) {
            OrderItem item = orderItemMapper.selectById(itemId);
            if (item == null || !item.getOrderNo().equals(orderNo)) {
                throw BusinessException.of(ErrorCodeEnum.REFUND_ITEM_INVALID.getCode(), "订单项不匹配");
            }
            if (item.getRefundStatus() != null && item.getRefundStatus() == 1) {
                throw BusinessException.of(ErrorCodeEnum.REFUND_ITEM_EXIST.getCode(), "该量表已退款");
            }
            refundAmount = refundAmount.add(item.getAmount());
        }

        long daysSincePay = 0;
        if (order.getPayTime() != null) {
            daysSincePay = java.time.Duration.between(order.getPayTime(), LocalDateTime.now()).toDays();
        }
        if (daysSincePay > BusinessConstant.REFUND_DAYS) {
            throw BusinessException.of(ErrorCodeEnum.REFUND_EXCEED.getCode(), "已超过退款时限（" + BusinessConstant.REFUND_DAYS + "天）");
        }

        String refundNo = "REF" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8);

        Refund refund = new Refund();
        refund.setOrderNo(orderNo);
        refund.setRefundNo(refundNo);
        refund.setUserId(order.getUserId());
        refund.setRefundAmount(refundAmount);
        refund.setRefundStatus(0);
        refund.setRefundReason(reason);
        refund.setRefundMethod(order.getPayMethod());
        refund.setCreateTime(LocalDateTime.now());
        refund.setUpdateTime(LocalDateTime.now());

        refundMapper.insert(refund);

        for (Long itemId : orderItemIds) {
            OrderItem item = orderItemMapper.selectById(itemId);
            item.setRefundStatus(2);
            item.setRefundAmount(item.getAmount());
            item.setUpdateTime(LocalDateTime.now());
            orderItemMapper.updateById(item);
        }

        checkAndUpdateOrderStatus(orderNo);

        log.info("创建部分退款申请: orderNo={}, refundNo={}, amount={}, items={}", 
            orderNo, refund.getRefundNo(), refundAmount, orderItemIds);

        RefundCreateResponse result = new RefundCreateResponse();
        result.setRefundId(refund.getId());
        result.setRefundNo(refund.getRefundNo());
        return result;
    }

    private void checkAndUpdateOrderStatus(String orderNo) {
        List<OrderItem> items = orderItemMapper.selectList(
            new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderNo, orderNo)
        );
        
        boolean allRefunded = items.stream()
            .allMatch(item -> item.getRefundStatus() != null && item.getRefundStatus() == 1);
        
        boolean partialRefunded = items.stream()
            .anyMatch(item -> item.getRefundStatus() != null && item.getRefundStatus() == 2);

        Order order = orderMapper.selectOne(
            new LambdaQueryWrapper<Order>().eq(Order::getOrderNo, orderNo)
        );
        
        if (allRefunded) {
            order.setOrderStatus(3);
        } else if (partialRefunded) {
            order.setOrderStatus(4);
        }
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    @Override
    public Refund getRefundById(Long id) {
        return refundMapper.selectById(id);
    }

    @Override
    public Refund getRefundByOrderNo(String orderNo) {
        return refundMapper.selectOne(
            new LambdaQueryWrapper<Refund>().eq(Refund::getOrderNo, orderNo)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveRefund(Long refundId) {
        Refund refund = refundMapper.selectById(refundId);
        if (refund == null) {
            throw BusinessException.of(ErrorCodeEnum.REFUND_NOT_FOUND.getCode(), "退款记录不存在");
        }

        if (refund.getRefundStatus() != 0) {
            throw BusinessException.of(ErrorCodeEnum.REFUND_STATUS_ERROR.getCode(), "退款状态不正确");
        }

        refund.setRefundStatus(1);
        refund.setRefundTime(LocalDateTime.now());
        refund.setUpdateTime(LocalDateTime.now());
        refundMapper.updateById(refund);

        if (refund.getOrderItemId() != null) {
            OrderItem item = orderItemMapper.selectById(refund.getOrderItemId());
            item.setRefundStatus(1);
            item.setUpdateTime(LocalDateTime.now());
            orderItemMapper.updateById(item);
        }

        checkAndUpdateOrderStatus(refund.getOrderNo());

        log.info("退款审批通过: refundNo={}", refund.getRefundNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectRefund(Long refundId, String reason) {
        Refund refund = refundMapper.selectById(refundId);
        if (refund == null) {
            throw BusinessException.of(ErrorCodeEnum.REFUND_NOT_FOUND.getCode(), "退款记录不存在");
        }

        refund.setRefundStatus(3);
        refund.setRejectReason(reason);
        refund.setUpdateTime(LocalDateTime.now());
        refundMapper.updateById(refund);

        if (refund.getOrderItemId() != null) {
            OrderItem item = orderItemMapper.selectById(refund.getOrderItemId());
            item.setRefundStatus(0);
            item.setRefundAmount(BigDecimal.ZERO);
            item.setUpdateTime(LocalDateTime.now());
            orderItemMapper.updateById(item);
        }

        checkAndUpdateOrderStatus(refund.getOrderNo());

        log.info("退款审批拒绝: refundNo={}, reason={}", refund.getRefundNo(), reason);
    }

    @Override
    public PageResult<Refund> getRefundList(PageRequest request, RefundListRequest params) {
        LambdaQueryWrapper<Refund> wrapper = new LambdaQueryWrapper<>();
        
        if (params != null) {
            if (params.getOrderNo() != null) {
                wrapper.eq(Refund::getOrderNo, params.getOrderNo());
            }
            if (params.getUserId() != null) {
                wrapper.eq(Refund::getUserId, params.getUserId());
            }
            if (params.getStatus() != null) {
                wrapper.eq(Refund::getRefundStatus, params.getStatus());
            }
        }
        
        wrapper.orderByDesc(Refund::getCreateTime);
        
        IPage<Refund> page = refundMapper.selectPage(
            new Page<>(request.getCurrent(), request.getSize()),
            wrapper
        );
        
        return PageResult.of(page.getRecords(), page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processRefundCallback(Map<String, Object> params) {
        String refundNo = (String) params.get("refundNo");
        String refundStatus = (String) params.get("refundStatus");
        
        Refund refund = refundMapper.selectOne(
            new LambdaQueryWrapper<Refund>().eq(Refund::getRefundNo, refundNo)
        );
        
        if (refund == null) {
            log.error("退款回调处理失败: 退款记录不存在, refundNo={}", refundNo);
            return;
        }

        if ("SUCCESS".equals(refundStatus)) {
            refund.setRefundStatus(1);
            refund.setTransactionId((String) params.get("transactionId"));
            refund.setRefundTime(LocalDateTime.now());
        } else {
            refund.setRefundStatus(2);
            refund.setRejectReason((String) params.get("failReason"));
        }
        
        refund.setUpdateTime(LocalDateTime.now());
        refundMapper.updateById(refund);
        
        log.info("退款回调处理完成: refundNo={}, status={}", refundNo, refundStatus);
    }
}
