package com.iotsic.ps.analysis.controller;

import com.iotsic.ps.analysis.dto.DashboardDTO;
import com.iotsic.ps.analysis.dto.EnterpriseUsageResponse;
import com.iotsic.ps.analysis.dto.ExportDataResponse;
import com.iotsic.ps.analysis.dto.IncomeReportResponse;
import com.iotsic.ps.analysis.dto.NormCompareDTO;
import com.iotsic.ps.analysis.dto.ReportExportRequest;
import com.iotsic.ps.analysis.dto.ResultDistributionResponse;
import com.iotsic.ps.analysis.dto.ScaleUsageReportResponse;
import com.iotsic.ps.analysis.dto.TrendDataResponse;
import com.iotsic.ps.analysis.dto.UserExamReportResponse;
import com.iotsic.ps.analysis.service.AnalysisService;
import com.iotsic.ps.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据分析控制器
 * 负责数据驾驶舱、统计报表、趋势分析、常模对比等请求
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    /**
     * 获取数据驾驶舱数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 驾驶舱数据
     */
    @GetMapping("/dashboard")
    public RestResult<DashboardDTO> getDashboard(
            @RequestParam(value = "startDate", required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "2026-03-11") String endDate) {
        DashboardDTO data = analysisService.getDashboardData(startDate, endDate);
        return RestResult.success(data);
    }

    /**
     * 获取量表使用报表
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 量表使用统计数据
     */
    @GetMapping("/report/scale")
    public RestResult<List<ScaleUsageReportResponse>> getScaleUsageReport(
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        List<ScaleUsageReportResponse> data = analysisService.getScaleUsageReport(startDate, endDate);
        return RestResult.success(data);
    }

    /**
     * 获取用户测评报表
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 用户测评统计数据
     */
    @GetMapping("/report/user")
    public RestResult<List<UserExamReportResponse>> getUserExamReport(
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        List<UserExamReportResponse> data = analysisService.getUserExamReport(startDate, endDate);
        return RestResult.success(data);
    }

    /**
     * 获取收入报表
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 收入统计数据
     */
    @GetMapping("/report/income")
    public RestResult<IncomeReportResponse> getIncomeReport(
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        IncomeReportResponse data = analysisService.getIncomeReport(startDate, endDate);
        return RestResult.success(data);
    }

    /**
     * 获取测评结果分布报表
     * 
     * @param scaleId 量表ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 结果分布数据
     */
    @GetMapping("/report/result")
    public RestResult<ResultDistributionResponse> getResultDistributionReport(
            @RequestParam(value = "scaleId") Long scaleId,
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        ResultDistributionResponse data = analysisService.getResultDistributionReport(scaleId, startDate, endDate);
        return RestResult.success(data);
    }

    /**
     * 获取企业使用报表
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 企业使用统计数据
     */
    @GetMapping("/report/enterprise")
    public RestResult<List<EnterpriseUsageResponse>> getEnterpriseUsageReport(
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        List<EnterpriseUsageResponse> data = analysisService.getEnterpriseUsageReport(startDate, endDate);
        return RestResult.success(data);
    }

    /**
     * 获取群体趋势分析
     * 
     * @param dimension 维度（age/gender）
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 趋势分析数据
     */
    @GetMapping("/trend/group")
    public RestResult<List<TrendDataResponse>> getGroupTrendAnalysis(
            @RequestParam(value = "dimension") String dimension,
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        List<TrendDataResponse> data = analysisService.getGroupTrendAnalysis(dimension, startDate, endDate);
        return RestResult.success(data);
    }

    /**
     * 常模对比分析
     * 
     * @param reportId 报告ID
     * @param normGroupId 常模组ID
     * @return 对比结果
     */
    @GetMapping("/norm/compare")
    public RestResult<NormCompareDTO> compareWithNorm(
            @RequestParam(value = "reportId") Long reportId,
            @RequestParam(value = "normGroupId", required = false) Long normGroupId) {
        NormCompareDTO data = analysisService.compareWithNorm(reportId, normGroupId);
        return RestResult.success(data);
    }

    /**
     * 导出报表数据
     * 
     * @param reportType 报表类型
     * @param request 导出参数
     * @return 导出结果
     */
    @PostMapping("/export")
    public RestResult<ExportDataResponse> exportReportData(
            @RequestParam(value = "reportType") String reportType,
            @RequestBody ReportExportRequest request) {
        ExportDataResponse data = analysisService.exportReportData(reportType, request);
        return RestResult.success(data);
    }
}
