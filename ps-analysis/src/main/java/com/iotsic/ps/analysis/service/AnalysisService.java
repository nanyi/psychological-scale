package com.iotsic.ps.analysis.service;

import com.iotsic.ps.analysis.dto.DashboardDTO;
import com.iotsic.ps.analysis.dto.NormCompareDTO;

import java.util.List;
import java.util.Map;

public interface AnalysisService {

    DashboardDTO getDashboardData(String startDate, String endDate);

    Map<String, Object> getScaleUsageReport(String startDate, String endDate);

    Map<String, Object> getUserExamReport(String startDate, String endDate);

    Map<String, Object> getIncomeReport(String startDate, String endDate);

    Map<String, Object> getResultDistributionReport(String startDate, String endDate);

    Map<String, Object> getEnterpriseUsageReport(String startDate, String endDate);

    List<Map<String, Object>> getGroupTrendAnalysis(String dimension, String startDate, String endDate);

    NormCompareDTO compareWithNorm(Long reportId, Long normGroupId);

    Map<String, Object> exportReportData(String reportType, Map<String, Object> params);
}
