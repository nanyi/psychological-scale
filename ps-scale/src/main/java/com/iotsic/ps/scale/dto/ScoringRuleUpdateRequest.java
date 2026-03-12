package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 计分规则更新请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScoringRuleUpdateRequest {

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
