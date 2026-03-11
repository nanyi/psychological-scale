package com.iotsic.ps.order.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.order.entity.EnterpriseQuota;

import java.util.Map;

public interface EnterpriseQuotaService {

    EnterpriseQuota createQuota(Long enterpriseId, Long scaleId, Integer quota, java.math.BigDecimal price);

    EnterpriseQuota getQuotaById(Long id);

    EnterpriseQuota getEnterpriseScaleQuota(Long enterpriseId, Long scaleId);

    void useQuota(Long enterpriseId, Long scaleId);

    void rechargeQuota(Long id, Integer quantity);

    EnterpriseQuota getQuotaByOrderId(Long orderId);

    PageResult<EnterpriseQuota> getEnterpriseQuotas(PageRequest request, Long enterpriseId);

    void expireQuota(Long id);
}
