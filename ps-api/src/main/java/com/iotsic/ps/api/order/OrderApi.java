package com.iotsic.ps.api.order;

import com.iotsic.ps.api.config.FeignConfig;
import com.iotsic.ps.api.dto.*;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.common.request.PageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ps-order", contextId = "orderApi", configuration = FeignConfig.class)
public interface OrderApi {

    @GetMapping("/api/order/detail/{id}")
    RestResult<OrderResponse> getOrderById(@PathVariable("id") Long id);

    @GetMapping("/api/order/by-no/{orderNo}")
    RestResult<OrderResponse> getOrderByNo(@PathVariable("orderNo") String orderNo);

    @PostMapping("/api/order/create")
    RestResult<OrderResponse> createOrder(@RequestBody OrderCreateRequest request);

    @PostMapping("/api/order/pay")
    RestResult<PayOrderResponse> payOrder(@RequestBody PayOrderRequest request);

    @PostMapping("/api/order/cancel")
    RestResult<Void> cancelOrder(@RequestParam("orderId") Long orderId);

    @PostMapping("/api/order/refund")
    RestResult<RefundOrderResponse> refundOrder(@RequestBody RefundOrderRequest request);

    @GetMapping("/api/order/by-user/{userId}")
    RestResult<OrderListResponse> getUserOrders(@PathVariable("userId") Long userId,
                                                  PageRequest pageRequest,
                                                  OrderQueryRequest queryRequest);
}
