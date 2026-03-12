package com.iotsic.ps.analysis.dto;

import lombok.Data;

/**
 * 用户测评报表响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserExamReportResponse {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 测评次数
     */
    private Long examCount;

    /**
     * 完成次数
     */
    private Long completedCount;

    /**
     * 平均得分
     */
    private java.math.BigDecimal avgScore;

    /**
     * 最近测评时间
     */
    private java.time.LocalDateTime lastExamTime;
}
