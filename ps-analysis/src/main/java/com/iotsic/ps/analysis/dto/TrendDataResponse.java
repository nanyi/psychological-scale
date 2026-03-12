package com.iotsic.ps.analysis.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 趋势数据响应DTO
 *
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class TrendDataResponse {

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 测评次数
     */
    private Long examCount;

    /**
     * 平均得分
     */
    private BigDecimal avgScore;

    /**
     * 异常率
     */
    private BigDecimal abnormalRate;
}
