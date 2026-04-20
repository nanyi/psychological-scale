package com.iotsic.ps.order.controller;

import com.iotsic.ps.order.dto.RefundApproveRequest;
import com.iotsic.ps.order.dto.RefundCreateRequest;
import com.iotsic.ps.order.dto.RefundCreateResponse;
import com.iotsic.ps.order.dto.RefundListRequest;
import com.iotsic.ps.order.dto.RefundRejectRequest;
import com.iotsic.ps.order.entity.Refund;
import com.iotsic.ps.order.service.RefundService;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 退款控制器
 * 负责退款申请、审批、查询等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/refund")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    /**
     * 创建退款申请
     * 
     * @param request 退款创建请求
     * @return 退款信息
     */
    @PostMapping("/create")
    public RestResult<RefundCreateResponse> createRefund(@RequestBody RefundCreateRequest request) {
        RefundCreateResponse refund = refundService.createRefund(
                request.getOrderNo(),
                request.getOrderItemIds(),
                request.getReason()
        );
        return RestResult.success("退款申请已提交", refund);
    }

    /**
     * 根据ID获取退款信息
     * 
     * @param id 退款ID
     * @return 退款信息
     */
    @GetMapping("/detail/{id}")
    public RestResult<Refund> getRefundById(@PathVariable Long id) {
        return RestResult.success(refundService.getRefundById(id));
    }

    /**
     * 根据订单编号获取退款信息
     * 
     * @param orderNo 订单编号
     * @return 退款信息
     */
    @GetMapping("/by-order/{orderNo}")
    public RestResult<Refund> getRefundByOrderNo(@PathVariable String orderNo) {
        return RestResult.success(refundService.getRefundByOrderNo(orderNo));
    }

    /**
     * 审批退款
     * 
     * @param request 退款审批请求
     * @return 操作结果
     */
    @PostMapping("/approve")
    public RestResult<Void> approveRefund(@RequestBody RefundApproveRequest request) {
        refundService.approveRefund(request.getRefundId());
        return RestResult.success();
    }

    /**
     * 拒绝退款
     * 
     * @param request 退款拒绝请求
     * @return 操作结果
     */
    @PostMapping("/reject")
    public RestResult<Void> rejectRefund(@RequestBody RefundRejectRequest request) {
        refundService.rejectRefund(request.getRefundId(), request.getReason());
        return RestResult.success();
    }

    /**
     * 获取退款列表
     * 
     * @param request 分页请求
     * @param refundListRequest 查询参数
     * @return 退款分页列表
     */
    @GetMapping("/list")
    public RestResult<PageResult<Refund>> getRefundList(
            PageRequest request,
            RefundListRequest refundListRequest) {
        return RestResult.success(refundService.getRefundList(request, refundListRequest));
    }

    /**
     * 处理退款回调
     * 
     * @param params 回调参数
     * @return 处理结果
     */
    @PostMapping("/callback")
    public RestResult<String> handleRefundCallback(@RequestBody Map<String, Object> params) {
        log.info("收到退款回调: {}", params);
        refundService.processRefundCallback(params);
        return RestResult.success("success");
    }
}
