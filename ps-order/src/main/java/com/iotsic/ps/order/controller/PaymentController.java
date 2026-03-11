package com.iotsic.ps.order.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.order.dto.PaymentCancelRequest;
import com.iotsic.ps.order.dto.PaymentCreateRequest;
import com.iotsic.ps.order.dto.PaymentStatusResponse;
import com.iotsic.ps.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付控制器
 * 负责支付订单创建、查询、取消等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 创建微信支付订单
     * 
     * @param request 支付创建请求
     * @return 支付结果
     */
    @PostMapping("/wechat/create")
    public RestResult<Map<String, Object>> createWechatPayOrder(@RequestBody PaymentCreateRequest request) {
        Map<String, Object> result = paymentService.createWechatPayOrder(
                request.getOrderId(),
                request.getReturnUrl() != null ? request.getReturnUrl() : ""
        );
        return RestResult.success("微信支付订单创建成功", result);
    }

    /**
     * 创建支付宝订单
     * 
     * @param request 支付创建请求
     * @return 支付结果
     */
    @PostMapping("/alipay/create")
    public RestResult<Map<String, Object>> createAlipayOrder(@RequestBody PaymentCreateRequest request) {
        Map<String, Object> result = paymentService.createAlipayOrder(
                request.getOrderId(),
                request.getReturnUrl() != null ? request.getReturnUrl() : ""
        );
        return RestResult.success("支付宝订单创建成功", result);
    }

    /**
     * 处理微信支付回调
     * 
     * @param params 回调参数
     * @return 处理结果
     */
    @PostMapping("/wechat/callback")
    public RestResult<String> handleWechatPayCallback(@RequestBody Map<String, Object> params) {
        log.info("收到微信支付回调: {}", params);
        paymentService.handleWechatPayCallback(params);
        return RestResult.success("success");
    }

    /**
     * 处理支付宝回调
     * 
     * @param params 回调参数
     * @return 处理结果
     */
    @PostMapping("/alipay/callback")
    public RestResult<String> handleAlipayCallback(@RequestBody Map<String, Object> params) {
        log.info("收到支付宝回调: {}", params);
        paymentService.handleAlipayCallback(params);
        return RestResult.success("success");
    }

    /**
     * 查询支付状态
     * 
     * @param orderId 订单ID
     * @return 支付状态
     */
    @GetMapping("/status/{orderId}")
    public RestResult<PaymentStatusResponse> queryPaymentStatus(@PathVariable Long orderId) {
        Map<String, Object> result = paymentService.queryPaymentStatus(orderId);
        
        PaymentStatusResponse response = new PaymentStatusResponse();
        response.setOrderId(orderId);
        response.setStatus((Integer) result.get("status"));
        response.setStatusDesc((String) result.get("statusDesc"));
        
        return RestResult.success(response);
    }

    /**
     * 取消支付
     * 
     * @param request 取消请求
     * @return 操作结果
     */
    @PostMapping("/cancel")
    public RestResult<Void> cancelPayment(@RequestBody PaymentCancelRequest request) {
        paymentService.cancelPayment(request.getOrderId());
        return RestResult.success();
    }
}
