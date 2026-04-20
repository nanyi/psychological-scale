package com.iotsic.ps.scale.report.controller;

import com.iotsic.ps.scale.report.entity.ReportTemplate;
import com.iotsic.ps.scale.report.service.ReportTemplateService;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 报告模板控制器
 * 负责报告模板的CRUD请求
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class ReportTemplateController {

    private final ReportTemplateService reportTemplateService;

    @PostMapping
    public RestResult<ReportTemplate> createTemplate(@RequestBody ReportTemplate template) {
        return RestResult.success(reportTemplateService.createTemplate(template));
    }

    @PutMapping
    public RestResult<ReportTemplate> updateTemplate(@RequestBody ReportTemplate template) {
        return RestResult.success(reportTemplateService.updateTemplate(template));
    }

    @GetMapping("/list")
    public RestResult<PageResult<ReportTemplate>> getTemplateList(
            PageRequest request,
            @RequestParam(required = false) Long scaleId,
            @RequestParam(required = false) Integer status) {
        return RestResult.success(reportTemplateService.getTemplateList(request, scaleId, status));
    }

    @GetMapping("/{templateId}")
    public RestResult<ReportTemplate> getTemplateById(@PathVariable Long templateId) {
        return RestResult.success(reportTemplateService.getTemplateById(templateId));
    }

    @GetMapping("/default")
    public RestResult<ReportTemplate> getDefaultTemplate(@RequestParam Long scaleId) {
        return RestResult.success(reportTemplateService.getDefaultTemplate(scaleId));
    }
}