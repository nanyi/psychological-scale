package com.iotsic.ps.order.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.order.entity.Refund;
import com.iotsic.ps.order.service.RefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/refund")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping("/create")
    public RestResult<Refund> createRefund(@RequestBody Map<String, Object> params) {
        String orderNo = (String) params.get("orderNo");
        List<Long> orderItemIds = (List<Long>) params.get("orderItemIds");
        String reason = (String) params.getOrDefault("reason", "用户申请退款");

        Refund refund = refundService.createRefund(orderNo, orderItemIds, reason);
        return RestResult.success("退款申请已提交", refund);
    }

    @GetMapping("/{id}")
    public RestResult<Refund> getRefundById(@PathVariable Long id) {
        return RestResult.success(refundService.getRefundById(id));
    }

    @GetMapping("/order/{orderNo}")
    public RestResult<Refund> getRefundByOrderNo(@PathVariable String orderNo) {
        return RestResult.success(refundService.getRefundByOrderNo(orderNo));
    }

    @PostMapping("/approve")
    public RestResult<Void> approveRefund(@RequestBody Map<String, Object> params) {
        Long refundId = Long.valueOf(params.get("refundId").toString());
        refundService.approveRefund(refundId);
        return RestResult.success();
    }

    @PostMapping("/reject")
    public RestResult<Void> rejectRefund(@RequestBody Map<String, Object> params) {
        Long refundId = Long.valueOf(params.get("refundId").toString());
        String reason = (String) params.getOrDefault("reason", "退款申请被拒绝");
        refundService.rejectRefund(refundId, reason);
        return RestResult.success();
    }

    @GetMapping("/list")
    public RestResult<PageResult<Refund>> getRefundList(
            PageRequest request,
            @RequestParam(required = false) Map<String, Object> params) {
        return RestResult.success(refundService.getRefundList(request, params));
    }

    @PostMapping("/callback")
    public RestResult<String> handleRefundCallback(@RequestBody Map<String, Object> params) {
        log.info("收到退款回调: {}", params);
        refundService.processRefundCallback(params);
        return RestResult.success("success");
    }
}
