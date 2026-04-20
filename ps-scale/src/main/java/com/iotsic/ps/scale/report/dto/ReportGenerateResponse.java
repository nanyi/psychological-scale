package com.iotsic.ps.scale.report.dto;

import lombok.Data;

/**
 * 报告生成响应DTO
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@Data
public class ReportGenerateResponse {

    /**
     * 报告ID
     */
    private Long reportId;

    /**
     * 报告编号
     */
    private String reportNo;

    /**
     * 状态
     */
    private Integer status;
}