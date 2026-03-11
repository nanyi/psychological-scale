package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 评分规则更新请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScoringRuleUpdateRequest {

    /**
     * 规则ID
     */
    private Long id;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 规则类型
     */
    private Integer ruleType;

    /**
     * 规则表达式
     */
    private String ruleExpression;
}
