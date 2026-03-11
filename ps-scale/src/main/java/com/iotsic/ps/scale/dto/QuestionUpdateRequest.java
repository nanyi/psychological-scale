package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 题目更新请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class QuestionUpdateRequest {

    /**
     * 题目ID
     */
    private Long id;

    /**
     * 维度ID
     */
    private Long dimensionId;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 题目类型
     */
    private Integer questionType;

    /**
     * 必答标识
     */
    private Integer required;
}
