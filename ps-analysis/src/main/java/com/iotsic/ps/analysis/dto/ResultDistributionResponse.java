package com.iotsic.ps.analysis.dto;

import lombok.Data;

/**
 * 结果分布响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ResultDistributionResponse {

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 量表名称
     */
    private String scaleName;

    /**
     * 分数区间分布
     */
    private java.util.List<ScoreRange> distribution;

    /**
     * 等级分布
     */
    private java.util.Map<String, Long> levelDistribution;

    @Data
    public static class ScoreRange {
        private Integer minScore;
        private Integer maxScore;
        private Long count;
    }
}
