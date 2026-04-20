package com.iotsic.ps.scale.report.dto;

import lombok.Data;

/**
 * 报告导出请求DTO
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@Data
public class ReportExportRequest {

    /**
     * 报告ID
     */
    private Long reportId;

    /**
     * 模板ID（可选）
     */
    private Long templateId;

    /**
     * 水印（可选）
     */
    private String watermark;

    /**
     * 纸张大小（PDF导出用）
     */
    private String pageSize = "A4";

    /**
     * 方向（PDF导出用）
     */
    private String orientation = "portrait";
}