package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 测评进度响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ExamProgressResponse {

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 总题数
     */
    private Integer totalQuestions;

    /**
     * 已答题目数
     */
    private Integer answeredCount;

    /**
     * 当前题目序号
     */
    private Integer currentIndex;

    /**
     * 剩余时间（秒）
     */
    private Integer remainingTime;

    /**
     * 完成状态
     */
    private String status;
}
