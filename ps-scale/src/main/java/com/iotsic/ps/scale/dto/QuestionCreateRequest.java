package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 题目创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class QuestionCreateRequest {

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 维度ID
     */
    private Integer dimensionId;

    /**
     * 题目序号
     */
    private Integer questionNo;

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
    private Boolean required;

    /**
     * 排序顺序
     */
    private Integer sortOrder;

    /**
     * 选项配置JSON字符串
     */
    private String options;
}
