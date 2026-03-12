package com.iotsic.ps.user.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.user.dto.EnterpriseCreateRequest;
import com.iotsic.ps.user.dto.EnterpriseUpdateRequest;
import com.iotsic.ps.core.entity.Enterprise;
import com.iotsic.ps.user.service.EnterpriseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 企业控制器
 * 负责企业的创建、查询、更新等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/enterprise")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    /**
     * 根据ID获取企业
     * 
     * @param id 企业ID
     * @return 企业信息
     */
    @GetMapping("/detail/{id}")
    public RestResult<Enterprise> getEnterpriseById(@PathVariable Long id) {
        return RestResult.success(enterpriseService.getEnterpriseById(id));
    }

    /**
     * 根据编码获取企业
     * 
     * @param code 企业编码
     * @return 企业信息
     */
    @GetMapping("/by-code/{code}")
    public RestResult<Enterprise> getEnterpriseByCode(@PathVariable String code) {
        return RestResult.success(enterpriseService.getEnterpriseByCode(code));
    }

    /**
     * 创建企业
     * 
     * @param request 企业创建请求
     * @return 企业信息
     */
    @PostMapping("/create")
    public RestResult<Enterprise> createEnterprise(@RequestBody EnterpriseCreateRequest request) {
        Enterprise enterprise = enterpriseService.createEnterprise(
                request.getName(),
                request.getCreditCode(),
                request.getContactPerson(),
                request.getContactPhone(),
                request.getContactEmail()
        );
        return RestResult.success("企业创建成功", enterprise);
    }

    /**
     * 更新企业
     * 
     * @param id 企业ID
     * @param request 企业更新请求
     * @return 操作结果
     */
    @PutMapping("/update/{id}")
    public RestResult<Void> updateEnterprise(@PathVariable Long id, @RequestBody EnterpriseUpdateRequest request) {
        enterpriseService.updateEnterprise(id, request);
        return RestResult.success();
    }

    /**
     * 禁用企业
     * 
     * @param id 企业ID
     * @return 操作结果
     */
    @PostMapping("/disable/{id}")
    public RestResult<Void> disableEnterprise(@PathVariable Long id) {
        enterpriseService.disableEnterprise(id);
        return RestResult.success();
    }

    /**
     * 启用企业
     * 
     * @param id 企业ID
     * @return 操作结果
     */
    @PostMapping("/enable/{id}")
    public RestResult<Void> enableEnterprise(@PathVariable Long id) {
        enterpriseService.enableEnterprise(id);
        return RestResult.success();
    }

    /**
     * 获取企业列表
     * 
     * @param request 分页请求
     * @return 企业分页列表
     */
    @GetMapping("/list")
    public RestResult<PageResult<Enterprise>> getEnterpriseList(PageRequest request) {
        return RestResult.success(enterpriseService.getEnterpriseList(request));
    }
}
