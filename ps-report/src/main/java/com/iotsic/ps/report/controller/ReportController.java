package com.iotsic.ps.report.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.report.entity.Report;
import com.iotsic.ps.report.service.ReportService;
import com.iotsic.ps.report.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final ExportService exportService;

    @PostMapping("/generate")
    public RestResult<Map<String, Object>> generateReport(@RequestBody Map<String, Long> params) {
        Long taskId = params.get("taskId");
        Long templateId = params.get("templateId");

        Report report = reportService.generateReport(taskId, templateId);

        Map<String, Object> data = new HashMap<>();
        data.put("reportId", report.getId());
        data.put("reportNo", report.getReportNo());
        data.put("status", report.getStatus());

        return RestResult.success(data);
    }

    @GetMapping("/detail")
    public RestResult<Report> getReportDetail(
            @RequestParam(required = false) Long reportId,
            @RequestParam(required = false) Long taskId) {
        
        Report report;
        if (reportId != null) {
            report = reportService.getReportDetail(reportId);
        } else if (taskId != null) {
            report = reportService.getReportByTaskId(taskId);
        } else {
            return RestResult.fail("请提供报告ID或任务ID");
        }

        return RestResult.success(report);
    }

    @GetMapping("/list")
    public RestResult<IPage<Report>> getReportList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long scaleId,
            @RequestParam(required = false) Integer status) {
        
        Page<Report> page = new Page<>(pageNum, pageSize);
        IPage<Report> result = reportService.getReportList(page, userId, scaleId, status);
        
        return RestResult.success(result);
    }

    @PostMapping("/export/word")
    public RestResult<Map<String, Object>> exportWord(@RequestBody Map<String, Object> params) {
        Long reportId = ((Number) params.get("reportId")).longValue();
        Long templateId = params.get("templateId") != null ? ((Number) params.get("templateId")).longValue() : null;
        String watermark = (String) params.get("watermark");

        Map<String, Object> result = exportService.exportWord(reportId, templateId, watermark);
        return RestResult.success(result);
    }

    @PostMapping("/export/pdf")
    public RestResult<Map<String, Object>> exportPdf(@RequestBody Map<String, Object> params) {
        Long reportId = ((Number) params.get("reportId")).longValue();
        Long templateId = params.get("templateId") != null ? ((Number) params.get("templateId")).longValue() : null;
        String pageSize = (String) params.getOrDefault("pageSize", "A4");
        String orientation = (String) params.getOrDefault("orientation", "portrait");
        String watermark = (String) params.get("watermark");

        Map<String, Object> result = exportService.exportPdf(reportId, templateId, pageSize, orientation, watermark);
        return RestResult.success(result);
    }

    @GetMapping("/download")
    public RestResult<Map<String, String>> downloadReport(
            @RequestParam String filePath,
            @RequestParam Long userId) {
        
        String downloadUrl = exportService.getDownloadUrl(filePath, userId);
        
        Map<String, String> result = new HashMap<>();
        result.put("downloadUrl", downloadUrl);
        
        return RestResult.success(result);
    }
}
