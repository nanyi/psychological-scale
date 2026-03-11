package com.ps.api.order;

import com.ps.api.config.FeignConfig;
import com.ps.common.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "ps-order", contextId = "orderApi", configuration = FeignConfig.class)
public interface OrderApi {

    @GetMapping("/api/order/{id}")
    RestResult<Map<String, Object>> getOrderById(@PathVariable("id") Long id);

    @GetMapping("/api/order/no/{orderNo}")
    RestResult<Map<String, Object>> getOrderByNo(@PathVariable("orderNo") String orderNo);

    @PostMapping("/api/order/create")
    RestResult<Map<String, Object>> createOrder(@RequestBody Map<String, Object> request);

    @PostMapping("/api/order/pay")
    RestResult<Map<String, Object>> payOrder(@RequestBody Map<String, Object> request);

    @PostMapping("/api/order/cancel")
    RestResult<Void> cancelOrder(@RequestParam("orderId") Long orderId);

    @PostMapping("/api/order/refund")
    RestResult<Map<String, Object>> refundOrder(@RequestBody Map<String, Object> request);

    @GetMapping("/api/order/user/{userId}")
    RestResult<Map<String, Object>> getUserOrders(@PathVariable("userId") Long userId,
                                                    @RequestParam Map<String, Object> params);
}
