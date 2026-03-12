package com.iotsic.ps.analysis.service;

import com.iotsic.ps.analysis.dto.DashboardDTO;
import com.iotsic.ps.analysis.dto.EnterpriseUsageResponse;
import com.iotsic.ps.analysis.dto.IncomeReportResponse;
import com.iotsic.ps.analysis.dto.NormCompareDTO;
import com.iotsic.ps.analysis.dto.ReportExportRequest;
import com.iotsic.ps.analysis.dto.ResultDistributionResponse;
import com.iotsic.ps.analysis.dto.ScaleUsageReportResponse;
import com.iotsic.ps.analysis.dto.UserExamReportResponse;

import java.util.List;
import java.util.Map;

public interface AnalysisService {

    DashboardDTO getDashboardData(String startDate, String endDate);

    List<ScaleUsageReportResponse> getScaleUsageReport(String startDate, String endDate);

    List<UserExamReportResponse> getUserExamReport(String startDate, String endDate);

    IncomeReportResponse getIncomeReport(String startDate, String endDate);

    ResultDistributionResponse getResultDistributionReport(Long scaleId, String startDate, String endDate);

    List<EnterpriseUsageResponse> getEnterpriseUsageReport(String startDate, String endDate);

    List<Map<String, Object>> getGroupTrendAnalysis(String dimension, String startDate, String endDate);

    NormCompareDTO compareWithNorm(Long reportId, Long normGroupId);

    Map<String, Object> exportReportData(String reportType, ReportExportRequest request);
}
