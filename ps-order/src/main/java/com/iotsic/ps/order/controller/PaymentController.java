package com.iotsic.ps.order.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/wechat/create")
    public RestResult<Map<String, Object>> createWechatPayOrder(@RequestBody Map<String, Object> params) {
        Long orderId = Long.valueOf(params.get("orderId").toString());
        String returnUrl = (String) params.getOrDefault("returnUrl", "");

        Map<String, Object> result = paymentService.createWechatPayOrder(orderId, returnUrl);
        return RestResult.success("微信支付订单创建成功", result);
    }

    @PostMapping("/alipay/create")
    public RestResult<Map<String, Object>> createAlipayOrder(@RequestBody Map<String, Object> params) {
        Long orderId = Long.valueOf(params.get("orderId").toString());
        String returnUrl = (String) params.getOrDefault("returnUrl", "");

        Map<String, Object> result = paymentService.createAlipayOrder(orderId, returnUrl);
        return RestResult.success("支付宝订单创建成功", result);
    }

    @PostMapping("/wechat/callback")
    public RestResult<String> handleWechatPayCallback(@RequestBody Map<String, Object> params) {
        log.info("收到微信支付回调: {}", params);
        paymentService.handleWechatPayCallback(params);
        return RestResult.success("success");
    }

    @PostMapping("/alipay/callback")
    public RestResult<String> handleAlipayCallback(@RequestBody Map<String, Object> params) {
        log.info("收到支付宝回调: {}", params);
        paymentService.handleAlipayCallback(params);
        return RestResult.success("success");
    }

    @GetMapping("/status/{orderId}")
    public RestResult<Map<String, Object>> queryPaymentStatus(@PathVariable Long orderId) {
        return RestResult.success(paymentService.queryPaymentStatus(orderId));
    }

    @PostMapping("/cancel")
    public RestResult<Void> cancelPayment(@RequestBody Map<String, Object> params) {
        Long orderId = Long.valueOf(params.get("orderId").toString());
        paymentService.cancelPayment(orderId);
        return RestResult.success();
    }
}
