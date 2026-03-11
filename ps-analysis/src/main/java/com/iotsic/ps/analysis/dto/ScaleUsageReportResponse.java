package com.iotsic.ps.analysis.dto;

import lombok.Data;

/**
 * 量表使用报表响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScaleUsageReportResponse {

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 量表名称
     */
    private String scaleName;

    /**
     * 使用次数
     */
    private Long usageCount;

    /**
     * 完成次数
     */
    private Long completedCount;

    /**
     * 完成率
     */
    private java.math.BigDecimal completionRate;

    /**
     * 平均得分
     */
    private java.math.BigDecimal avgScore;
}
