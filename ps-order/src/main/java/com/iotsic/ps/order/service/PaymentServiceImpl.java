package com.iotsic.ps.order.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.order.dto.PaymentResponse;
import com.iotsic.ps.order.entity.Order;
import com.iotsic.ps.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderMapper orderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentResponse createWechatPayOrder(Long orderId, String returnUrl) {
        Order order = getOrderForPayment(orderId);

        Map<String, Object> payParams = new HashMap<>();
        payParams.put("orderNo", order.getOrderNo());
        payParams.put("amount", order.getActualAmount().multiply(BigDecimal.valueOf(100)).intValue());
        payParams.put("returnUrl", returnUrl);
        payParams.put("payMethod", "WECHAT");

        PaymentResponse result = new PaymentResponse();
        result.setPrepayId("wx" + UUID.randomUUID().toString().replace("-", "").substring(0, 32));
        result.setOrderNo(order.getOrderNo());
        result.setAmount(order.getActualAmount());
        result.setPayUrl("weixin://wxpay/bizpayurl?pr=" + result.getPrepayId());

        log.info("创建微信支付订单: orderId={}, amount={}", orderId, order.getActualAmount());

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentResponse createAlipayOrder(Long orderId, String returnUrl) {
        Order order = getOrderForPayment(orderId);

        Map<String, Object> payParams = new HashMap<>();
        payParams.put("orderNo", order.getOrderNo());
        payParams.put("amount", order.getActualAmount());
        payParams.put("returnUrl", returnUrl);
        payParams.put("payMethod", "ALIPAY");

        PaymentResponse result = new PaymentResponse();
        result.setOrderNo(order.getOrderNo());
        result.setAmount(order.getActualAmount());
        result.setPayUrl("https://openapi.alipay.com/gateway.do?order_no=" + order.getOrderNo());

        log.info("创建支付宝订单: orderId={}, amount={}", orderId, order.getActualAmount());

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleWechatPayCallback(Map<String, Object> params) {
        String orderNo = (String) params.get("orderNo");
        String transactionId = (String) params.get("transactionId");

        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getOrderNo, orderNo);
        Order order = orderMapper.selectOne(wrapper);

        if (order == null) {
            log.error("微信支付回调订单不存在: orderNo={}", orderNo);
            return;
        }

        if (order.getOrderStatus() != 0) {
            log.info("订单已支付: orderNo={}, status={}", orderNo, order.getOrderStatus());
            return;
        }

        order.setOrderStatus(1);
        order.setPayMethod("WECHAT");
        order.setTransactionId(transactionId);
        order.setPayTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);

        log.info("微信支付成功: orderNo={}, transactionId={}", orderNo, transactionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleAlipayCallback(Map<String, Object> params) {
        String orderNo = (String) params.get("out_trade_no");
        String tradeNo = (String) params.get("trade_no");

        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getOrderNo, orderNo);
        Order order = orderMapper.selectOne(wrapper);

        if (order == null) {
            log.error("支付宝回调订单不存在: orderNo={}", orderNo);
            return;
        }

        if (order.getOrderStatus() != 0) {
            log.info("订单已支付: orderNo={}, status={}", orderNo, order.getOrderStatus());
            return;
        }

        order.setOrderStatus(1);
        order.setPayMethod("ALIPAY");
        order.setTransactionId(tradeNo);
        order.setPayTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);

        log.info("支付宝支付成功: orderNo={}, tradeNo={}", orderNo, tradeNo);
    }

    @Override
    public Map<String, Object> queryPaymentStatus(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw BusinessException.of(ErrorCodeEnum.ORDER_NOT_FOUND.getCode(), "订单不存在");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("orderNo", order.getOrderNo());
        result.put("status", order.getOrderStatus());
        result.put("payMethod", order.getPayMethod());
        result.put("transactionId", order.getTransactionId());
        result.put("payTime", order.getPayTime());

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelPayment(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw BusinessException.of(ErrorCodeEnum.ORDER_NOT_FOUND.getCode(), "订单不存在");
        }

        if (order.getOrderStatus() != 0) {
            throw BusinessException.of(ErrorCodeEnum.ORDER_CANNOT_CANCEL.getCode(), "订单状态不允许取消");
        }

        order.setOrderStatus(2);
        order.setCancelReason("支付取消");
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);

        log.info("支付取消: orderId={}", orderId);
    }

    private Order getOrderForPayment(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getDeleted()) {
            throw BusinessException.of(ErrorCodeEnum.ORDER_NOT_FOUND.getCode(), "订单不存在");
        }

        if (order.getOrderStatus() != 0) {
            throw BusinessException.of(ErrorCodeEnum.ORDER_PAID.getCode(), "订单已支付或已取消");
        }

        if (order.getExpireTime() != null && order.getExpireTime().isBefore(LocalDateTime.now())) {
            throw BusinessException.of(ErrorCodeEnum.ORDER_EXPIRED.getCode(), "订单已过期");
        }

        return order;
    }
}
