package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 测评提交响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ExamSubmitResponse {

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 提交状态
     */
    private String status;

    /**
     * 总题数
     */
    private Integer totalQuestions;

    /**
     * 已答题目数
     */
    private Integer answeredCount;

    /**
     * 提交时间
     */
    private java.time.LocalDateTime submitTime;
}
