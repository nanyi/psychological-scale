package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 测评记录列表查询请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ExamRecordListRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 状态
     */
    private Integer status;
}
