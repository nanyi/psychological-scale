package com.iotsic.ps.order.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.order.entity.Refund;

import java.util.List;
import java.util.Map;

public interface RefundService {

    Refund createRefund(String orderNo, List<Long> orderItemIds, String reason);

    Refund getRefundById(Long id);

    Refund getRefundByOrderNo(String orderNo);

    void approveRefund(Long refundId);

    void rejectRefund(Long refundId, String reason);

    PageResult<Refund> getRefundList(PageRequest request, Map<String, Object> params);

    void processRefundCallback(Map<String, Object> params);
}
