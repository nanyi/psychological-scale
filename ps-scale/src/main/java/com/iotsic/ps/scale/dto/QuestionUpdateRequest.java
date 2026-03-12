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
     * 题目内容
     */
    private String content;

    /**
     * 题目类型
     */
    private Integer questionType;

    /**
     * 题目选项（JSON格式）
     */
    private String options;

    /**
     * 必答标记
     */
    private Boolean required;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 题目分组
     */
    private String groupName;
}
