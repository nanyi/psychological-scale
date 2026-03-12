package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 测评提交结果响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ExamSubmitResultResponse {

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 记录编号
     */
    private String recordNo;

    /**
     * 总分
     */
    private BigDecimal totalScore;

    /**
     * 维度得分
     */
    private Map<String, Object> dimensionScores;

    /**
     * 结果解释
     */
    private String interpretation;

    /**
     * 提交时间
     */
    private LocalDateTime submitTime;
}
