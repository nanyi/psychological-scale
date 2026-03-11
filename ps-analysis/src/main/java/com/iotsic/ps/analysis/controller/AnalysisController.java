package com.iotsic.ps.analysis.controller;

import com.iotsic.ps.analysis.dto.DashboardDTO;
import com.iotsic.ps.analysis.dto.NormCompareDTO;
import com.iotsic.ps.analysis.service.AnalysisService;
import com.iotsic.ps.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    @GetMapping("/dashboard")
    public RestResult<DashboardDTO> getDashboard(
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        DashboardDTO data = analysisService.getDashboardData(startDate, endDate);
        return RestResult.success(data);
    }

    @GetMapping("/report/scale")
    public RestResult<Map<String, Object>> getScaleUsageReport(
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        Map<String, Object> data = analysisService.getScaleUsageReport(startDate, endDate);
        return RestResult.success(data);
    }

    @GetMapping("/report/user")
    public RestResult<Map<String, Object>> getUserExamReport(
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        Map<String, Object> data = analysisService.getUserExamReport(startDate, endDate);
        return RestResult.success(data);
    }

    @GetMapping("/report/income")
    public RestResult<Map<String, Object>> getIncomeReport(
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        Map<String, Object> data = analysisService.getIncomeReport(startDate, endDate);
        return RestResult.success(data);
    }

    @GetMapping("/report/result")
    public RestResult<Map<String, Object>> getResultDistributionReport(
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        Map<String, Object> data = analysisService.getResultDistributionReport(startDate, endDate);
        return RestResult.success(data);
    }

    @GetMapping("/report/enterprise")
    public RestResult<Map<String, Object>> getEnterpriseUsageReport(
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        Map<String, Object> data = analysisService.getEnterpriseUsageReport(startDate, endDate);
        return RestResult.success(data);
    }

    @GetMapping("/trend/group")
    public RestResult<List<Map<String, Object>>> getGroupTrendAnalysis(
            @RequestParam String dimension,
            @RequestParam(required = false, defaultValue = "2026-01-01") String startDate,
            @RequestParam(required = false, defaultValue = "2026-03-11") String endDate) {
        List<Map<String, Object>> data = analysisService.getGroupTrendAnalysis(dimension, startDate, endDate);
        return RestResult.success(data);
    }

    @GetMapping("/norm/compare")
    public RestResult<NormCompareDTO> compareWithNorm(
            @RequestParam Long reportId,
            @RequestParam(required = false) Long normGroupId) {
        NormCompareDTO data = analysisService.compareWithNorm(reportId, normGroupId);
        return RestResult.success(data);
    }

    @PostMapping("/export")
    public RestResult<Map<String, Object>> exportReportData(
            @RequestParam String reportType,
            @RequestBody Map<String, Object> params) {
        Map<String, Object> data = analysisService.exportReportData(reportType, params);
        return RestResult.success(data);
    }
}
