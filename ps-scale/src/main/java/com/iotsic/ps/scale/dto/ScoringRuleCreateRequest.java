package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 计分规则创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScoringRuleCreateRequest {

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 维度ID
     */
    private Long dimensionId;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 计分方式：1-正向计分，2-反向计分
     */
    private Integer scoringType;

    /**
     * 最低分
     */
    private Integer minScore;

    /**
     * 最高分
     */
    private Integer maxScore;
}
