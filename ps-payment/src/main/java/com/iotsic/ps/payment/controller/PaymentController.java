package com.iotsic.ps.payment.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.payment.dto.*;
import com.iotsic.ps.payment.entity.Payment;
import com.iotsic.ps.payment.service.PaymentService;
import com.iotsic.ps.payment.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付控制器
 * 负责处理支付创建、退款、回调等请求
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final RefundService refundService;

    /**
     * 创建微信支付订单
     * 
     * @param request 支付创建请求
     * @return 支付结果，包含支付二维码链接
     */
    @PostMapping("/wxpay/create")
    public RestResult<PaymentCreateResponse> createWxPayOrder(@RequestBody PaymentCreateRequest request) {
        PaymentCreateResponse result = paymentService.createWxPayOrder(
                request.getOrderId(),
                request.getOrderNo(),
                request.getAmount(),
                request.getDescription()
        );
        return RestResult.success(result);
    }

    /**
     * 创建支付宝订单
     * 
     * @param request 支付创建请求
     * @return 支付结果
     */
    @PostMapping("/alipay/create")
    public RestResult<PaymentCreateResponse> createAliPayOrder(@RequestBody PaymentCreateRequest request) {
        PaymentCreateResponse result = paymentService.createAliPayOrder(
                request.getOrderId(),
                request.getOrderNo(),
                request.getAmount(),
                request.getDescription()
        );
        return RestResult.success(result);
    }

    /**
     * 微信支付回调通知
     * 
     * @param notifyData 回调数据
     * @return 响应结果
     */
    @PostMapping("/wxpay/notify")
    public String handleWxPayNotify(@RequestBody Map<String, String> notifyData) {
        paymentService.handleWxPayNotify(notifyData);
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    /**
     * 支付宝回调通知
     * 
     * @param notifyData 回调数据
     * @return 响应结果
     */
    @PostMapping("/alipay/notify")
    public String handleAliPayNotify(@RequestBody Map<String, String> notifyData) {
        paymentService.handleAliPayNotify(notifyData);
        return "success";
    }

    /**
     * 获取支付详情
     * 
     * @param orderId 订单ID
     * @return 支付记录
     */
    @GetMapping("/detail")
    public RestResult<Payment> getPaymentDetail(@RequestParam Long orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        return RestResult.success(payment);
    }

    /**
     * 创建退款申请
     * 
     * @param request 退款请求
     * @return 退款结果
     */
    @PostMapping("/refund/create")
    public RestResult<RefundCreateResponse> createRefund(@RequestBody RefundCreateRequest request) {
        RefundCreateResponse result = refundService.createRefund(
                request.getPaymentId(),
                request.getRefundAmount(),
                request.getRefundReason()
        );
        return RestResult.success(result);
    }

    /**
     * 微信退款回调通知
     * 
     * @param notifyData 回调数据
     * @return 响应结果
     */
    @PostMapping("/refund/wxpay/notify")
    public String handleWxRefundNotify(@RequestBody Map<String, String> notifyData) {
        refundService.handleWxRefundNotify(notifyData);
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    /**
     * 支付宝退款回调通知
     * 
     * @param notifyData 回调数据
     * @return 响应结果
     */
    @PostMapping("/refund/alipay/notify")
    public String handleAliRefundNotify(@RequestBody Map<String, String> notifyData) {
        refundService.handleAliRefundNotify(notifyData);
        return "success";
    }
}
