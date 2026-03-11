package com.iotsic.ps.order.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.order.entity.EnterpriseQuota;
import com.iotsic.ps.order.service.EnterpriseQuotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/enterprise-quota")
@RequiredArgsConstructor
public class EnterpriseQuotaController {

    private final EnterpriseQuotaService enterpriseQuotaService;

    @PostMapping("/create")
    public RestResult<EnterpriseQuota> createQuota(@RequestBody Map<String, Object> params) {
        Long enterpriseId = Long.valueOf(params.get("enterpriseId").toString());
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        Integer quota = Integer.valueOf(params.get("quota").toString());
        java.math.BigDecimal price = new java.math.BigDecimal(params.get("price").toString());

        EnterpriseQuota result = enterpriseQuotaService.createQuota(enterpriseId, scaleId, quota, price);
        return RestResult.success("企业配额创建成功", result);
    }

    @GetMapping("/{id}")
    public RestResult<EnterpriseQuota> getQuotaById(@PathVariable Long id) {
        return RestResult.success(enterpriseQuotaService.getQuotaById(id));
    }

    @GetMapping("/enterprise/{enterpriseId}/scale/{scaleId}")
    public RestResult<EnterpriseQuota> getEnterpriseScaleQuota(
            @PathVariable Long enterpriseId,
            @PathVariable Long scaleId) {
        return RestResult.success(enterpriseQuotaService.getEnterpriseScaleQuota(enterpriseId, scaleId));
    }

    @PostMapping("/use")
    public RestResult<Void> useQuota(@RequestBody Map<String, Object> params) {
        Long enterpriseId = Long.valueOf(params.get("enterpriseId").toString());
        Long scaleId = Long.valueOf(params.get("scaleId").toString());

        enterpriseQuotaService.useQuota(enterpriseId, scaleId);
        return RestResult.success();
    }

    @PostMapping("/recharge")
    public RestResult<Void> rechargeQuota(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        Integer quantity = Integer.valueOf(params.get("quantity").toString());

        enterpriseQuotaService.rechargeQuota(id, quantity);
        return RestResult.success();
    }

    @GetMapping("/list")
    public RestResult<PageResult<EnterpriseQuota>> getEnterpriseQuotas(
            PageRequest request,
            @RequestParam(required = false) Long enterpriseId) {
        return RestResult.success(enterpriseQuotaService.getEnterpriseQuotas(request, enterpriseId));
    }

    @PostMapping("/{id}/expire")
    public RestResult<Void> expireQuota(@PathVariable Long id) {
        enterpriseQuotaService.expireQuota(id);
        return RestResult.success();
    }
}
