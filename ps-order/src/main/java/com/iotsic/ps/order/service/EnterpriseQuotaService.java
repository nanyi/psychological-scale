package com.iotsic.ps.order.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.order.entity.EnterpriseQuota;

import java.math.BigDecimal;

/**
 * 企业配额服务接口
 * 负责企业配额的创建、使用、充值、查询等业务操作
 *
 * @author Ryan
 * @since 2026-03-12
 */
public interface EnterpriseQuotaService {

    /**
     * 创建配额
     *
     * @param enterpriseId 企业ID
     * @param scaleId 量表ID
     * @param quota 配额数量
     * @param price 价格
     * @return 企业配额实体
     */
    EnterpriseQuota createQuota(Long enterpriseId, Long scaleId, Integer quota, BigDecimal price);

    /**
     * 根据ID获取配额
     *
     * @param id 配额ID
     * @return 企业配额实体
     */
    EnterpriseQuota getQuotaById(Long id);

    /**
     * 获取企业量表配额
     *
     * @param enterpriseId 企业ID
     * @param scaleId 量表ID
     * @return 企业配额实体
     */
    EnterpriseQuota getEnterpriseScaleQuota(Long enterpriseId, Long scaleId);

    /**
     * 使用配额
     *
     * @param enterpriseId 企业ID
     * @param scaleId 量表ID
     */
    void useQuota(Long enterpriseId, Long scaleId);

    /**
     * 充值配额
     *
     * @param id 配额ID
     * @param quantity 充值数量
     */
    void rechargeQuota(Long id, Integer quantity);

    /**
     * 根据订单ID获取配额
     *
     * @param orderId 订单ID
     * @return 企业配额实体
     */
    EnterpriseQuota getQuotaByOrderId(Long orderId);

    /**
     * 获取企业配额列表
     *
     * @param request 分页请求
     * @param enterpriseId 企业ID
     * @return 配额分页结果
     */
    PageResult<EnterpriseQuota> getEnterpriseQuotas(PageRequest request, Long enterpriseId);

    /**
     * 过期配额
     *
     * @param id 配额ID
     */
    void expireQuota(Long id);
}
