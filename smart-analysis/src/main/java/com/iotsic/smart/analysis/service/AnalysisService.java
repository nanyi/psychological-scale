package com.iotsic.smart.analysis.service;

import com.iotsic.smart.analysis.dto.DashboardDTO;
import com.iotsic.smart.analysis.dto.EnterpriseUsageResponse;
import com.iotsic.smart.analysis.dto.ExportDataResponse;
import com.iotsic.smart.analysis.dto.IncomeReportResponse;
import com.iotsic.smart.analysis.dto.NormCompareDTO;
import com.iotsic.smart.analysis.dto.ReportExportRequest;
import com.iotsic.smart.analysis.dto.ResultDistributionResponse;
import com.iotsic.smart.analysis.dto.ScaleUsageReportResponse;
import com.iotsic.smart.analysis.dto.TrendDataResponse;
import com.iotsic.smart.analysis.dto.UserExamReportResponse;

import java.util.List;

public interface AnalysisService {

    DashboardDTO getDashboardData(String startDate, String endDate);

    List<ScaleUsageReportResponse> getScaleUsageReport(String startDate, String endDate);

    List<UserExamReportResponse> getUserExamReport(String startDate, String endDate);

    IncomeReportResponse getIncomeReport(String startDate, String endDate);

    ResultDistributionResponse getResultDistributionReport(Long scaleId, String startDate, String endDate);

    List<EnterpriseUsageResponse> getEnterpriseUsageReport(String startDate, String endDate);

    /**
     * 获取群体趋势分析
     *
     * @param dimension 分析维度
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 趋势数据列表
     */
    List<TrendDataResponse> getGroupTrendAnalysis(String dimension, String startDate, String endDate);

    NormCompareDTO compareWithNorm(Long reportId, Long normGroupId);

    ExportDataResponse exportReportData(String reportType, ReportExportRequest request);
}
