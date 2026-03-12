package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 测评记录统计响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ExamRecordStatisticsResponse {

    /**
     * 总测评次数
     */
    private Long totalCount;

    /**
     * 完成数量
     */
    private Long completedCount;

    /**
     * 进行中数量
     */
    private Long inProgressCount;

    /**
     * 已放弃数量
     */
    private Long abandonedCount;

    /**
     * 平均完成时间（秒）
     */
    private Integer avgCompleteTime;

    /**
     * 平均得分
     */
    private java.math.BigDecimal avgScore;
}
