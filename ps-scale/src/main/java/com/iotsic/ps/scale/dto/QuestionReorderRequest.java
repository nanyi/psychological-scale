package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 题目排序请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class QuestionReorderRequest {

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 题目ID列表
     */
    private java.util.List<Long> questionIds;
}
