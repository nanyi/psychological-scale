package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 测评记录详情响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ExamRecordDetailResponse {

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 量表名称
     */
    private String scaleName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 开始时间
     */
    private java.time.LocalDateTime startTime;

    /**
     * 结束时间
     */
    private java.time.LocalDateTime endTime;

    /**
     * 总题数
     */
    private Integer totalQuestions;

    /**
     * 已答题目数
     */
    private Integer answeredCount;

    /**
     * 总分
     */
    private Integer totalScore;
}
