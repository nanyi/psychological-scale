package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 测评提交请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ExamSubmitRequest {

    /**
     * 测评记录ID
     */
    private Long recordId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 答题记录列表
     */
    private List<Map<String, Object>> answers;
}
