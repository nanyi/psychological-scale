package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.util.Map;

/**
 * 分数解读请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScoreInterpretRequest {

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 维度分数
     */
    private Map<String, Object> dimensionScores;

    /**
     * 总分
     */
    private Integer totalScore;
}
