package com.iotsic.ps.scale.dto;

import lombok.Data;

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
     * 维度ID
     */
    private Long dimensionId;
}
