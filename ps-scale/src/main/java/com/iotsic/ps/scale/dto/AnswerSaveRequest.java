package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.util.Map;

/**
 * 答案保存请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class AnswerSaveRequest {

    /**
     * 测评记录ID
     */
    private Long recordId;

    /**
     * 题目ID
     */
    private Long questionId;

    /**
     * 答案内容（单选为String，多选为JSON数组）
     */
    private String answer;

    /**
     * 答题时间（秒）
     */
    private Integer answerTime;

    /**
     * 扩展答案（用于特殊题型）
     */
    private Map<String, Object> extAnswer;
}
