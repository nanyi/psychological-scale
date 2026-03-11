package com.iotsic.ps.analysis.dto;

import lombok.Data;

/**
 * 企业使用报表响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class EnterpriseUsageResponse {

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 测评次数
     */
    private Long examCount;

    /**
     * 配额使用数
     */
    private Long quotaUsed;

    /**
     * 配额总数
     */
    private Long quotaTotal;

    /**
     * 使用率
     */
    private java.math.BigDecimal usageRate;
}
