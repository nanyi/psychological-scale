package com.iotsic.ps.payment.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.payment.entity.Payment;
import com.iotsic.ps.payment.service.PaymentService;
import com.iotsic.ps.payment.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final RefundService refundService;

    @PostMapping("/wxpay/create")
    public RestResult<Map<String, Object>> createWxPayOrder(@RequestBody Map<String, Object> params) {
        Long orderId = ((Number) params.get("orderId")).longValue();
        String orderNo = (String) params.get("orderNo");
        BigDecimal amount = new BigDecimal(params.get("amount").toString());
        String description = (String) params.getOrDefault("description", "心理测评订单");

        Map<String, Object> result = paymentService.createWxPayOrder(orderId, orderNo, amount, description);
        return RestResult.success(result);
    }

    @PostMapping("/alipay/create")
    public RestResult<Map<String, Object>> createAliPayOrder(@RequestBody Map<String, Object> params) {
        Long orderId = ((Number) params.get("orderId")).longValue();
        String orderNo = (String) params.get("orderNo");
        BigDecimal amount = new BigDecimal(params.get("amount").toString());
        String description = (String) params.getOrDefault("description", "心理测评订单");

        Map<String, Object> result = paymentService.createAliPayOrder(orderId, orderNo, amount, description);
        return RestResult.success(result);
    }

    @PostMapping("/wxpay/notify")
    public String handleWxPayNotify(@RequestBody Map<String, String> notifyData) {
        paymentService.handleWxPayNotify(notifyData);
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    @PostMapping("/alipay/notify")
    public String handleAliPayNotify(@RequestBody Map<String, String> notifyData) {
        paymentService.handleAliPayNotify(notifyData);
        return "success";
    }

    @GetMapping("/detail")
    public RestResult<Payment> getPaymentDetail(@RequestParam Long orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        return RestResult.success(payment);
    }

    @PostMapping("/refund/create")
    public RestResult<Map<String, Object>> createRefund(@RequestBody Map<String, Object> params) {
        Long paymentId = ((Number) params.get("paymentId")).longValue();
        BigDecimal refundAmount = new BigDecimal(params.get("refundAmount").toString());
        String refundReason = (String) params.get("refundReason");

        Map<String, Object> result = refundService.createRefund(paymentId, refundAmount, refundReason);
        return RestResult.success(result);
    }

    @PostMapping("/refund/wxpay/notify")
    public String handleWxRefundNotify(@RequestBody Map<String, String> notifyData) {
        refundService.handleWxRefundNotify(notifyData);
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    @PostMapping("/refund/alipay/notify")
    public String handleAliRefundNotify(@RequestBody Map<String, String> notifyData) {
        refundService.handleAliRefundNotify(notifyData);
        return "success";
    }
}
