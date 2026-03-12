package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 分数计算响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScoreCalculateResponse {

    /**
     * 总分
     */
    private Integer totalScore;

    /**
     * 各维度得分
     */
    private Map<String, BigDecimal> dimensionScores;

    /**
     * 得分等级
     */
    private String level;

    /**
     * 解读建议
     */
    private String suggestion;
}
