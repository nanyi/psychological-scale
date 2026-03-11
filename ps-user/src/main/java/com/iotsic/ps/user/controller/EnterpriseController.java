package com.iotsic.ps.user.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.core.entity.Enterprise;
import com.iotsic.ps.user.service.EnterpriseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/enterprise")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @GetMapping("/{id}")
    public RestResult<Enterprise> getEnterpriseById(@PathVariable Long id) {
        return RestResult.success(enterpriseService.getEnterpriseById(id));
    }

    @GetMapping("/code/{code}")
    public RestResult<Enterprise> getEnterpriseByCode(@PathVariable String code) {
        return RestResult.success(enterpriseService.getEnterpriseByCode(code));
    }

    @PostMapping("/create")
    public RestResult<Enterprise> createEnterprise(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String code = (String) request.get("code");
        String contact = (String) request.get("contact");
        String phone = (String) request.get("phone");
        String email = (String) request.get("email");

        Enterprise enterprise = enterpriseService.createEnterprise(name, code, contact, phone, email);
        return RestResult.success("企业创建成功", enterprise);
    }

    @PutMapping("/{id}")
    public RestResult<Void> updateEnterprise(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        enterpriseService.updateEnterprise(id, params);
        return RestResult.success();
    }

    @PostMapping("/{id}/disable")
    public RestResult<Void> disableEnterprise(@PathVariable Long id) {
        enterpriseService.disableEnterprise(id);
        return RestResult.success();
    }

    @PostMapping("/{id}/enable")
    public RestResult<Void> enableEnterprise(@PathVariable Long id) {
        enterpriseService.enableEnterprise(id);
        return RestResult.success();
    }

    @GetMapping("/list")
    public RestResult<PageResult<Enterprise>> getEnterpriseList(PageRequest request) {
        return RestResult.success(enterpriseService.getEnterpriseList(request));
    }
}
