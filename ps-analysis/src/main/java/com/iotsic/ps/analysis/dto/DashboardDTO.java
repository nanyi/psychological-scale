package com.iotsic.ps.analysis.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class DashboardDTO {

    private Long totalExams;
    private Long activeUsers;
    private BigDecimal completionRate;
    private BigDecimal avgDuration;
    private BigDecimal abnormalRate;
    private List<ScaleUsageDTO> scaleRankings;
    private List<TrendDataDTO> trendData;
    private Map<String, Object> summary;

    @Data
    public static class ScaleUsageDTO {
        private Long scaleId;
        private String scaleName;
        private Long usageCount;
        private BigDecimal ratio;
    }

    @Data
    public static class TrendDataDTO {
        private String date;
        private Long examCount;
        private Long userCount;
    }
}
