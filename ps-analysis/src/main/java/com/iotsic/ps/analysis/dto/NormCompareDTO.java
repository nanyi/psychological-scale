package com.iotsic.ps.analysis.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class NormCompareDTO {

    private Long reportId;
    private Long scaleId;
    private Long dimensionId;
    private String dimensionName;
    private BigDecimal rawScore;
    private BigDecimal tScore;
    private BigDecimal zScore;
    private BigDecimal percentile;
   Rank;
    private String level;
    private String comparison;
    private NormDataDTO normData;

    @Data
    public static class NormDataDTO {
        private String normGroup;
        private BigDecimal meanScore;
        private BigDecimal stdDeviation;
        private BigDecimal percentile50;
        private BigDecimal percentile75;
        private BigDecimal percentile90;
    }
}
