package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 测评开始请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ExamStartRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 量表ID
     */
    private Long scaleId;
}
