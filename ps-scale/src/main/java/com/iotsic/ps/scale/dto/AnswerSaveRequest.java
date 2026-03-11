package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 答题保存请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class AnswerSaveRequest {

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 题目ID
     */
    private Long questionId;

    /**
     * 答案值
     */
    private String answerValue;
}
