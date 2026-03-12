package com.iotsic.ps.thirdparty.dto;

import lombok.Data;

import java.util.Map;

/**
 * 平台报告响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class PlatformReportResponse {

    /**
     * 外部记录编号
     */
    private String externalRecordId;

    /**
     * 报告状态：completed/processing/failed
     */
    private String reportStatus;

    /**
     * 报告URL
     */
    private String reportUrl;

    /**
     * 报告内容
     */
    private String reportContent;

    /**
     * 报告数据（扩展字段）
     */
    private Map<String, Object> reportData;
}
