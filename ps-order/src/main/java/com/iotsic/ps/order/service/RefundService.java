package com.iotsic.ps.order.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.order.entity.Refund;

import java.util.Map;

public interface RefundService {

    Refund createRefund(Long orderId, String reason);

    Refund getRefundById(Long id);

    Refund getRefundByOrderId(Long orderId);

    void approveRefund(Long refundId);

    void rejectRefund(Long refundId, String reason);

    PageResult<Refund> getRefundList(PageRequest request, Map<String, Object> params);

    void processRefundCallback(Map<String, Object> params);
}
