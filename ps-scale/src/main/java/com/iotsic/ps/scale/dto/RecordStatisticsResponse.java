package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 测评记录统计响应DTO
 *
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class RecordStatisticsResponse {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 总测评次数
     */
    private Integer totalExams;

    /**
     * 完成次数
     */
    private Integer completedExams;

    /**
     * 完成率
     */
    private BigDecimal completionRate;

    /**
     * 平均得分
     */
    private BigDecimal avgScore;

    /**
     * 最高得分
     */
    private BigDecimal maxScore;

    /**
     * 最低得分
     */
    private BigDecimal minScore;

    /**
     * 平均时长（秒）
     */
    private BigDecimal avgDuration;

    /**
     * 测评量表数
     */
    private Integer scaleCount;
}
