package com.iotsic.ps.thirdparty.dto;

import lombok.Data;

/**
 * 同步统计响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class SyncStatisticsResponse {

    /**
     * 总量表数
     */
    private Integer totalScales;

    /**
     * 已同步数
     */
    private Integer syncedCount;

    /**
     * 同步失败数
     */
    private Integer failedCount;

    /**
     * 最后同步时间
     */
    private java.time.LocalDateTime lastSyncTime;

    /**
     * 同步状态
     */
    private String status;
}
