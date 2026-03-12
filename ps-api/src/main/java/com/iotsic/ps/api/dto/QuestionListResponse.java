package com.iotsic.ps.api.dto;

import com.iotsic.ps.core.entity.Question;
import lombok.Data;

import java.util.List;

/**
 * 题目列表响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class QuestionListResponse {

    /**
     * 题目列表
     */
    private List<Question> questions;

    /**
     * 总数
     */
    private Integer totalCount;
}
