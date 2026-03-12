package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.math.BigDecimal;

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
     * 记录编号
     */
    private String recordNo;

    /**
     * 总题数
     */
    private Integer totalQuestions;

    /**
     * 已答题目数
     */
    private Integer answeredQuestions;

    /**
     * 完成进度百分比
     */
    private BigDecimal progressPercent;

    /**
     * 当前题目序号
     */
    private Integer currentQuestionNo;

    /**
     * 剩余时间（秒）
     */
    private Integer remainingTime;

    /**
     * 状态（0-进行中,1-已完成,2-已暂停）
     */
    private Integer status;
}
