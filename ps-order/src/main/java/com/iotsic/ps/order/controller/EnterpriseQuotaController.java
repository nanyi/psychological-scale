package com.iotsic.ps.order.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.order.dto.QuotaCreateRequest;
import com.iotsic.ps.order.dto.QuotaRechargeRequest;
import com.iotsic.ps.order.dto.QuotaUseRequest;
import com.iotsic.ps.order.entity.EnterpriseQuota;
import com.iotsic.ps.order.service.EnterpriseQuotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 企业配额控制器
 * 负责企业配额的创建、使用、充值等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/enterprise-quota")
@RequiredArgsConstructor
public class EnterpriseQuotaController {

    private final EnterpriseQuotaService enterpriseQuotaService;

    /**
     * 创建企业配额
     * 
     * @param request 配额创建请求
     * @return 配额信息
     */
    @PostMapping("/create")
    public RestResult<EnterpriseQuota> createQuota(@RequestBody QuotaCreateRequest request) {
        EnterpriseQuota result = enterpriseQuotaService.createQuota(
                request.getEnterpriseId(),
                request.getScaleId(),
                request.getQuota(),
                request.getPrice()
        );
        return RestResult.success("企业配额创建成功", result);
    }

    /**
     * 根据ID获取配额
     * 
     * @param id 配额ID
     * @return 配额信息
     */
    @GetMapping("/detail/{id}")
    public RestResult<EnterpriseQuota> getQuotaById(@PathVariable Long id) {
        return RestResult.success(enterpriseQuotaService.getQuotaById(id));
    }

    /**
     * 获取企业量表配额
     * 
     * @param enterpriseId 企业ID
     * @param scaleId 量表ID
     * @return 配额信息
     */
    @GetMapping("/by-enterprise/{enterpriseId}/scale/{scaleId}")
    public RestResult<EnterpriseQuota> getEnterpriseScaleQuota(
            @PathVariable Long enterpriseId,
            @PathVariable Long scaleId) {
        return RestResult.success(enterpriseQuotaService.getEnterpriseScaleQuota(enterpriseId, scaleId));
    }

    /**
     * 使用配额
     * 
     * @param request 配额使用请求
     * @return 操作结果
     */
    @PostMapping("/use")
    public RestResult<Void> useQuota(@RequestBody QuotaUseRequest request) {
        enterpriseQuotaService.useQuota(request.getEnterpriseId(), request.getScaleId());
        return RestResult.success();
    }

    /**
     * 充值配额
     * 
     * @param request 配额充值请求
     * @return 操作结果
     */
    @PostMapping("/recharge")
    public RestResult<Void> rechargeQuota(@RequestBody QuotaRechargeRequest request) {
        enterpriseQuotaService.rechargeQuota(request.getEnterpriseId(), request.getQuantity());
        return RestResult.success();
    }

    /**
     * 获取企业配额列表
     * 
     * @param request 分页请求
     * @param enterpriseId 企业ID
     * @return 配额分页列表
     */
    @GetMapping("/list")
    public RestResult<PageResult<EnterpriseQuota>> getEnterpriseQuotas(
            PageRequest request,
            @RequestParam(value = "enterpriseId", required = false) Long enterpriseId) {
        return RestResult.success(enterpriseQuotaService.getEnterpriseQuotas(request, enterpriseId));
    }

    /**
     * 过期配额
     * 
     * @param id 配额ID
     * @return 操作结果
     */
    @PostMapping("/expire/{id}")
    public RestResult<Void> expireQuota(@PathVariable Long id) {
        enterpriseQuotaService.expireQuota(id);
        return RestResult.success();
    }
}
