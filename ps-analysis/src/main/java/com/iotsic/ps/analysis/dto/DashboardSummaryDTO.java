package com.iotsic.ps.analysis.dto;

import lombok.Data;

/**
 * 仪表盘摘要DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class DashboardSummaryDTO {

    /**
     * 今日测评次数
     */
    private Long todayExams;

    /**
     * 今日用户数
     */
    private Long todayUsers;

    /**
     * 周增长率
     */
    private String weekGrowth;

    /**
     * 月增长率
     */
    private String monthGrowth;
}
