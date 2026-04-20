package com.iotsic.ps.report.controller;

import com.iotsic.ps.report.entity.ReportTemplate;
import com.iotsic.ps.report.service.ReportTemplateService;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report/template")
@RequiredArgsConstructor
public class ReportTemplateController {

    private final ReportTemplateService reportTemplateService;

    @PostMapping("/create")
    public RestResult<ReportTemplate> createTemplate(@RequestBody ReportTemplate template) {
        ReportTemplate result = reportTemplateService.createTemplate(template);
        return RestResult.success(result);
    }

    @PutMapping("/update")
    public RestResult<ReportTemplate> updateTemplate(@RequestBody ReportTemplate template) {
        ReportTemplate result = reportTemplateService.updateTemplate(template);
        return RestResult.success(result);
    }

    @GetMapping("/list")
    public RestResult<PageResult<ReportTemplate>> getTemplateList(
            PageRequest request,
            @RequestParam(value = "scaleId", required = false) Long scaleId,
            @RequestParam(value = "status", required = false) Integer status) {

        PageResult<ReportTemplate> result = reportTemplateService.getTemplateList(request, scaleId, status);
        
        return RestResult.success(result);
    }

    @GetMapping("/detail")
    public RestResult<ReportTemplate> getTemplateDetail(@RequestParam Long templateId) {
        ReportTemplate template = reportTemplateService.getTemplateById(templateId);
        return RestResult.success(template);
    }
}
