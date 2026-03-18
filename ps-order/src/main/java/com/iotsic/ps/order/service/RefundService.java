package com.iotsic.ps.order.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.order.dto.RefundCreateResponse;
import com.iotsic.ps.order.dto.RefundListRequest;
import com.iotsic.ps.order.entity.Refund;

import java.util.List;
import java.util.Map;

/**
 * 退款服务接口
 * 负责退款的创建、审批、查询、回调处理等业务操作
 *
 * @author Ryan
 * @since 2026-03-12
 */
public interface RefundService {

    /**
     * 创建退款申请
     *
     * @param orderNo 订单号
     * @param orderItemIds 订单项ID列表
     * @param reason 退款原因
     * @return 退款实体
     */
    RefundCreateResponse createRefund(String orderNo, List<Long> orderItemIds, String reason);

    /**
     * 根据ID获取退款
     *
     * @param id 退款ID
     * @return 退款实体
     */
    Refund getRefundById(Long id);

    /**
     * 根据订单号获取退款
     *
     * @param orderNo 订单号
     * @return 退款实体
     */
    Refund getRefundByOrderNo(String orderNo);

    /**
     * 审批退款
     *
     * @param refundId 退款ID
     */
    void approveRefund(Long refundId);

    /**
     * 拒绝退款
     *
     * @param refundId 退款ID
     * @param reason 拒绝原因
     */
    void rejectRefund(Long refundId, String reason);

    /**
     * 获取退款列表
     *
     * @param request 分页请求
     * @param params 查询参数
     * @return 退款分页结果
     */
    PageResult<Refund> getRefundList(PageRequest request, RefundListRequest params);

    /**
     * 处理退款回调
     *
     * @param params 回调参数
     */
    void processRefundCallback(Map<String, Object> params);
}
