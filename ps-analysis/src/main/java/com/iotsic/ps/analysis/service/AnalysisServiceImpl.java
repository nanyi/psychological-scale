package com.iotsic.ps.analysis.service;

import com.iotsic.ps.analysis.dto.DashboardDTO;
import com.iotsic.ps.analysis.dto.NormCompareDTO;
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
    public Map<String, Object> getScaleUsageReport(String startDate, String endDate) {
        Map<String, Object> result = new HashMap<>();
        
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("scaleName", "SCL-90症状自评量表");
        item1.put("usageCount", 4500);
        item1.put("avgScore", 125.5);
        item1.put("abnormalRate", 0.12);
        data.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("scaleName", "SDS抑郁自评量表");
        item2.put("usageCount", 3200);
        item2.put("avgScore", 42.3);
        item2.put("abnormalRate", 0.18);
        data.add(item2);

        result.put("data", data);
        result.put("total", data.size());
        return result;
    }

    @Override
    public Map<String, Object> getUserExamReport(String startDate, String endDate) {
        Map<String, Object> result = new HashMap<>();
        result.put("totalUsers", 5800);
        result.put("activeUsers", 3280);
        result.put("newUsers", 520);
        result.put("repeatRate", 0.65);
        return result;
    }

    @Override
    public Map<String, Object> getIncomeReport(String startDate, String endDate) {
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", 285000.00);
        result.put("orderCount", 1250);
        result.put("avgAmount", 228.00);
        result.put("refundAmount", 8500.00);
        return result;
    }

    @Override
    public Map<String, Object> getResultDistributionReport(String startDate, String endDate) {
        Map<String, Object> result = new HashMap<>();
        
        List<Map<String, Object>> distribution = new ArrayList<>();
        Map<String, Object> normal = new HashMap<>();
        normal.put("level", "正常");
        normal.put("count", 8800);
        normal.put("ratio", 0.70);
        distribution.add(normal);

        Map<String, Object> mild = new HashMap<>();
        mild.put("level", "轻度异常");
        mild.put("count", 2000);
        mild.put("ratio", 0.16);
        distribution.add(mild);

        Map<String, Object> moderate = new HashMap<>();
        moderate.put("level", "中度异常");
        moderate.put("count", 1200);
        moderate.put("ratio", 0.095);
        distribution.add(moderate);

        Map<String, Object> severe = new HashMap<>();
        severe.put("level", "重度异常");
        severe.put("count", 580);
        severe.put("ratio", 0.046);
        distribution.add(severe);

        result.put("distribution", distribution);
        result.put("total", 12580);
        return result;
    }

    @Override
    public Map<String, Object> getEnterpriseUsageReport(String startDate, String endDate) {
        Map<String, Object> result = new HashMap<>();
        result.put("totalEnterprises", 85);
        result.put("activeEnterprises", 62);
        result.put("totalEmployees", 5200);
        result.put("avgExamsPerEmployee", 2.4);
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
        result.settScore(new BigDecimal("55.2"));
        result.setzScore(new BigDecimal("0.52"));
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
    public Map<String, Object> exportReportData(String reportType, Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        result.put("fileName", reportType + "_" + System.currentTimeMillis() + ".xlsx");
        result.put("fileUrl", "/exports/" + reportType + "_" + System.currentTimeMillis() + ".xlsx");
        result.put("recordCount", 1000);
        return result;
    }
}
