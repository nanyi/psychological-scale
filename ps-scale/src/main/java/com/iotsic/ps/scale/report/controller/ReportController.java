package com.iotsic.ps.scale.report.controller;

import com.iotsic.ps.core.entity.Report;
import com.iotsic.ps.scale.report.dto.ReportDownloadResponse;
import com.iotsic.ps.scale.report.dto.ReportExportRequest;
import com.iotsic.ps.scale.report.dto.ReportExportResponse;
import com.iotsic.ps.scale.report.dto.ReportGenerateRequest;
import com.iotsic.ps.scale.report.dto.ReportGenerateResponse;
import com.iotsic.ps.scale.report.service.ExportService;
import com.iotsic.ps.scale.report.service.ReportService;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 报告控制器
 * 负责报告生成、查看、导出等请求
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final ExportService exportService;

    @PostMapping("/generate")
    public RestResult<ReportGenerateResponse> generateReport(@RequestBody ReportGenerateRequest request) {
        return RestResult.success();
    }

    @GetMapping("/list")
    public RestResult<PageResult<Report>> getReportList(
            PageRequest request,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long scaleId,
            @RequestParam(required = false) Integer status) {
        return RestResult.success(reportService.getReportList(request, userId, scaleId, status));
    }

    @GetMapping("/{reportId}")
    public RestResult<Report> getReportDetail(@PathVariable Long reportId) {
        return RestResult.success(reportService.getReportDetail(reportId));
    }

    @PostMapping("/export/word")
    public RestResult<ReportExportResponse> exportWord(@RequestBody ReportExportRequest request) {
        return RestResult.success(exportService.exportWord(request.getReportId(), 
                request.getTemplateId(), request.getWatermark()));
    }

    @PostMapping("/export/pdf")
    public RestResult<ReportExportResponse> exportPdf(@RequestBody ReportExportRequest request) {
        return RestResult.success(exportService.exportPdf(request.getReportId(),
                request.getTemplateId(), request.getPageSize(), 
                request.getOrientation(), request.getWatermark()));
    }

    @GetMapping("/download")
    public RestResult<ReportDownloadResponse> getDownloadUrl(
            @RequestParam String filePath,
            @RequestParam Long userId) {
        return RestResult.success(exportService.getDownloadUrl(filePath, userId));
    }
}