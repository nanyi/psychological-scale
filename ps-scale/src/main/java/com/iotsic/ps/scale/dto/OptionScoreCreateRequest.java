package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 选项分数创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class OptionScoreCreateRequest {

    /**
     * 题目ID
     */
    private Long questionId;

    /**
     * 选项值
     */
    private String optionValue;

    /**
     * 分值
     */
    private Integer score;

    /**
     * 维度ID
     */
    private Long dimensionId;
}
