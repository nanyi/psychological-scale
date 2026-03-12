package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.util.Map;

/**
 * 评分计算请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScoreCalculateRequest {

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 维度ID
     */
    private Long dimensionId;

    /**
     * 答案列表（题目ID -> 答案）
     */
    private Map<Long, String> answers;
}
