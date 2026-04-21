package com.iotsic.ps.scale.report.dto;

import lombok.Data;

/**
 * 报告生成请求DTO
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@Data
public class ReportGenerateRequest {

    /**
     * 测评任务ID
     */
    private Long taskId;

    /**
     * 报告模板ID（可选）
     */
    private Long templateId;
}
