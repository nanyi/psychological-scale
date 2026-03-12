package com.iotsic.ps.analysis.service;

import com.iotsic.ps.analysis.dto.DashboardDTO;
import com.iotsic.ps.analysis.dto.EnterpriseUsageResponse;
import com.iotsic.ps.analysis.dto.IncomeReportResponse;
import com.iotsic.ps.analysis.dto.NormCompareDTO;
import com.iotsic.ps.analysis.dto.ReportExportRequest;
import com.iotsic.ps.analysis.dto.ResultDistributionResponse;
import com.iotsic.ps.analysis.dto.ScaleUsageReportResponse;
import com.iotsic.ps.analysis.dto.UserExamReportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    @Override
    public DashboardDTO getDashboardData(String startDate, String endDate) {
        DashboardDTO dashboard = new DashboardDTO();

        dashboard.setTotalExams(12580L);
        dashboard.setActiveUsers(3280L);
        dashboard.setCompletionRate(new BigDecimal("85.5"));
        dashboard.setAvgDuration(new BigDecimal("25.6"));
        dashboard.setAbnormalRate(new BigDecimal("12.3"));

        List<DashboardDTO.ScaleUsageDTO> scaleRankings = new ArrayList<>();
        DashboardDTO.ScaleUsageDTO scale1 = new DashboardDTO.ScaleUsageDTO();
        scale1.setScaleId(1L);
        scale1.setScaleName("SCL-90症状自评量表");
        scale1.setUsageCount(4500L);
        scale1.setRatio(new BigDecimal("35.8"));
        scaleRankings.add(scale1);

        DashboardDTO.ScaleUsageDTO scale2 = new DashboardDTO.ScaleUsageDTO();
        scale2.setScaleId(2L);
        scale2.setScaleName("SDS抑郁自评量表");
        scale2.setUsageCount(3200L);
        scale2.setRatio(new BigDecimal("25.4"));
        scaleRankings.add(scale2);

        dashboard.setScaleRankings(scaleRankings);

        List<DashboardDTO.TrendDataDTO> trendData = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            DashboardDTO.TrendDataDTO trend = new DashboardDTO.TrendDataDTO();
            trend.setDate(date.toString());
            trend.setExamCount((long) (Math.random() * 100 + 50));
            trend.setUserCount((long) (Math.random() * 30 + 10));
            trendData.add(trend);
        }
        dashboard.setTrendData(trendData);

        Map<String, Object> summary = new HashMap<>();
        summary.put("todayExams", 156L);
        summary.put("todayUsers", 45L);
        summary.put("weekGrowth", "+12.5%");
        summary.put("monthGrowth", "+25.8%");
        dashboard.setSummary(summary);

        return dashboard;
    }

    @Override
    public List<ScaleUsageReportResponse> getScaleUsageReport(String startDate, String endDate) {
        List<ScaleUsageReportResponse> result = new ArrayList<>();
        
        ScaleUsageReportResponse item1 = new ScaleUsageReportResponse();
        item1.setScaleId(1L);
        item1.setScaleName("SCL-90症状自评量表");
        item1.setUsageCount(4500L);
        item1.setCompletedCount(4200L);
        item1.setCompletionRate(new BigDecimal("0.93"));
        item1.setAvgScore(new BigDecimal("125.5"));
        result.add(item1);

        ScaleUsageReportResponse item2 = new ScaleUsageReportResponse();
        item2.setScaleId(2L);
        item2.setScaleName("SDS抑郁自评量表");
        item2.setUsageCount(3200L);
        item2.setCompletedCount(2900L);
        item2.setCompletionRate(new BigDecimal("0.91"));
        item2.setAvgScore(new BigDecimal("42.3"));
        result.add(item2);

        return result;
    }

    @Override
    public List<UserExamReportResponse> getUserExamReport(String startDate, String endDate) {
        List<UserExamReportResponse> result = new ArrayList<>();
        
        UserExamReportResponse item = new UserExamReportResponse();
        item.setUserId(1L);
        item.setUsername("testuser");
        item.setExamCount(5800L);
        item.setCompletedCount(5200L);
        item.setAvgScore(new BigDecimal("85.5"));
        result.add(item);
        
        return result;
    }

    @Override
    public IncomeReportResponse getIncomeReport(String startDate, String endDate) {
        IncomeReportResponse result = new IncomeReportResponse();
        result.setTotalIncome(new BigDecimal("285000.00"));
        result.setWechatIncome(new BigDecimal("180000.00"));
        result.setAlipayIncome(new BigDecimal("105000.00"));
        result.setOrderCount(1250L);
        result.setRefundAmount(new BigDecimal("8500.00"));
        result.setNetIncome(new BigDecimal("276500.00"));
        return result;
    }

    @Override
    public ResultDistributionResponse getResultDistributionReport(Long scaleId, String startDate, String endDate) {
        ResultDistributionResponse result = new ResultDistributionResponse();
        result.setScaleId(scaleId);
        result.setScaleName("SCL-90症状自评量表");
        
        List<ResultDistributionResponse.ScoreRange> distribution = new ArrayList<>();
        ResultDistributionResponse.ScoreRange range1 = new ResultDistributionResponse.ScoreRange();
        range1.setMinScore(0);
        range1.setMaxScore(50);
        range1.setCount(3500L);
        distribution.add(range1);
        
        ResultDistributionResponse.ScoreRange range2 = new ResultDistributionResponse.ScoreRange();
        range2.setMinScore(51);
        range2.setMaxScore(100);
        range2.setCount(4500L);
        distribution.add(range2);
        
        result.setDistribution(distribution);
        
        Map<String, Long> levelDistribution = new HashMap<>();
        levelDistribution.put("正常", 8800L);
        levelDistribution.put("轻度异常", 2000L);
        levelDistribution.put("中度异常", 1200L);
        levelDistribution.put("重度异常", 580L);
        result.setLevelDistribution(levelDistribution);
        
        return result;
    }

    @Override
    public List<EnterpriseUsageResponse> getEnterpriseUsageReport(String startDate, String endDate) {
        List<EnterpriseUsageResponse> result = new ArrayList<>();
        
        EnterpriseUsageResponse item = new EnterpriseUsageResponse();
        item.setEnterpriseId(1L);
        item.setEnterpriseName("测试企业");
        item.setExamCount(5200L);
        item.setQuotaUsed(4500L);
        item.setQuotaTotal(5000L);
        item.setUsageRate(new BigDecimal("0.90"));
        result.add(item);
        
        return result;
    }

    @Override
    public List<Map<String, Object>> getGroupTrendAnalysis(String dimension, String startDate, String endDate) {
        List<Map<String, Object>> result = new ArrayList<>();
        
        if ("age".equals(dimension)) {
            Map<String, Object> group1 = new HashMap<>();
            group1.put("groupName", "18-25岁");
            group1.put("examCount", 3500);
            group1.put("avgScore", 118.5);
            group1.put("abnormalRate", 0.11);
            result.add(group1);

            Map<String, Object> group2 = new HashMap<>();
            group2.put("groupName", "26-35岁");
            group2.put("examCount", 4200);
            group2.put("avgScore", 125.2);
            group2.put("abnormalRate", 0.14);
            result.add(group2);
        } else if ("gender".equals(dimension)) {
            Map<String, Object> male = new HashMap<>();
            male.put("groupName", "男性");
            male.put("examCount", 5800);
            male.put("avgScore", 120.3);
            male.put("abnormalRate", 0.10);
            result.add(male);

            Map<String, Object> female = new HashMap<>();
            female.put("groupName", "女性");
            female.put("examCount", 6780);
            female.put("avgScore", 128.7);
            female.put("abnormalRate", 0.15);
            result.add(female);
        }

        return result;
    }

    @Override
    public NormCompareDTO compareWithNorm(Long reportId, Long normGroupId) {
        NormCompareDTO result = new NormCompareDTO();
        result.setReportId(reportId);
        result.setRawScore(new BigDecimal("145.5"));
        result.setTScore(new BigDecimal("55.2"));
        result.setZScore(new BigDecimal("0.52"));
        result.setPercentile(new BigDecimal("69.8"));
        result.setLevel("正常");
        result.setComparison("略高于常模平均水平");

        NormCompareDTO.NormDataDTO normData = new NormCompareDTO.NormDataDTO();
        normData.setNormGroup("18-35岁男性");
        normData.setMeanScore(new BigDecimal("120.0"));
        normData.setStdDeviation(new BigDecimal("25.0"));
        normData.setPercentile50(new BigDecimal("115.0"));
        normData.setPercentile75(new BigDecimal("140.0"));
        normData.setPercentile90(new BigDecimal("165.0"));
        result.setNormData(normData);

        return result;
    }

    @Override
    public Map<String, Object> exportReportData(String reportType, ReportExportRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("fileName", reportType + "_" + System.currentTimeMillis() + ".xlsx");
        result.put("fileUrl", "/exports/" + reportType + "_" + System.currentTimeMillis() + ".xlsx");
        result.put("recordCount", 1000);
        return result;
    }
}
