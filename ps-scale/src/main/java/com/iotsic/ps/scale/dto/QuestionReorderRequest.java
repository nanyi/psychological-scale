package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.util.List;

/**
 * 题目重排请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class QuestionReorderRequest {

    /**
     * 题目ID列表（按顺序排列）
     */
    private List<Long> questionIds;

    /**
     * 量表ID
     */
    private Long scaleId;
}
