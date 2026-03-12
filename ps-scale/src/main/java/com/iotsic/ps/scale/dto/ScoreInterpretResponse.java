package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 分数解读响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScoreInterpretResponse {

    /**
     * 得分等级
     */
    private String level;

    /**
     * 等级描述
     */
    private String description;

    /**
     * 解读建议
     */
    private String suggestion;

    /**
     * 参考范围
     */
    private String referenceRange;

    /**
     * 详细解读文本
     */
    private String interpretation;
}
