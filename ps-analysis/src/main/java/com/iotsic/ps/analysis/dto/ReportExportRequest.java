package com.iotsic.ps.analysis.dto;

import lombok.Data;

/**
 * 报表导出请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ReportExportRequest {

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 企业ID
     */
    private Long enterpriseId;
}
