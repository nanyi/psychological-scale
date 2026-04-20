package com.iotsic.ps.report.controller;

import com.iotsic.ps.core.entity.Report;
import com.iotsic.ps.report.dto.ReportDownloadResponse;
import com.iotsic.ps.report.dto.ReportExportRequest;
import com.iotsic.ps.report.dto.ReportExportResponse;
import com.iotsic.ps.report.dto.ReportGenerateRequest;
import com.iotsic.ps.report.dto.ReportGenerateResponse;
import com.iotsic.ps.report.service.ExportService;
import com.iotsic.ps.report.service.ReportService;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 生成测评报告
     * 
     * @param request 报告生成请求
     * @return 报告生成结果
     */
    @PostMapping("/generate")
    public RestResult<ReportGenerateResponse> generateReport(@RequestBody ReportGenerateRequest request) {
        Report report = reportService.generateReport(request.getTaskId(), request.getTemplateId());

        ReportGenerateResponse response = new ReportGenerateResponse();
        response.setReportId(report.getId());
        response.setReportNo(report.getReportNo());
        response.setStatus(report.getStatus());

        return RestResult.success(response);
    }

    /**
     * 获取报告详情
     * 
     * @param reportId 报告ID
     * @param taskId 任务ID（二选一）
     * @return 报告详情
     */
    @GetMapping("/detail")
    public RestResult<Report> getReportDetail(
            @RequestParam(value = "reportId", required = false) Long reportId,
            @RequestParam(value = "taskId", required = false) Long taskId) {
        
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

    /**
     * 获取报告列表
     * 
     * @param request 分页参数
     * @param userId 用户ID
     * @param scaleId 量表ID
     * @param status 状态
     * @return 报告分页列表
     */
    @GetMapping("/list")
    public RestResult<PageResult<Report>> getReportList(
            PageRequest request,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "scaleId", required = false) Long scaleId,
            @RequestParam(value = "status", required = false) Integer status) {

        PageResult<Report> result = reportService.getReportList(request, userId, scaleId, status);
        
        return RestResult.success(result);
    }

    /**
     * 导出Word报告
     * 
     * @param request 导出请求参数
     * @return 导出结果
     */
    @PostMapping("/export/word")
    public RestResult<ReportExportResponse> exportWord(@RequestBody ReportExportRequest request) {
        ReportExportResponse result = exportService.exportWord(
                request.getReportId(), 
                request.getTemplateId(), 
                request.getWatermark()
        );
        
        return RestResult.success(result);
    }

    /**
     * 导出PDF报告
     * 
     * @param request 导出请求参数
     * @return 导出结果
     */
    @PostMapping("/export/pdf")
    public RestResult<ReportExportResponse> exportPdf(@RequestBody ReportExportRequest request) {
        ReportExportResponse result = exportService.exportPdf(
                request.getReportId(), 
                request.getTemplateId(), 
                request.getPageSize(), 
                request.getOrientation(), 
                request.getWatermark()
        );
        
        return RestResult.success(result);
    }

    /**
     * 下载报告
     * 
     * @param filePath 文件路径
     * @param userId 用户ID
     * @return 下载链接
     */
    @GetMapping("/download")
    public RestResult<ReportDownloadResponse> downloadReport(
            @RequestParam String filePath,
            @RequestParam Long userId) {

        ReportDownloadResponse result = exportService.getDownloadUrl(filePath, userId);

        return RestResult.success(result);
    }
}
