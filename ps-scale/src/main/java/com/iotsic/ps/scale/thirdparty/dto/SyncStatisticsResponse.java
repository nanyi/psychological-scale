package com.iotsic.ps.scale.thirdparty.dto;

import lombok.Data;

import java.util.Map;

/**
 * 同步统计响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class SyncStatisticsResponse {

    /**
     * 总同步次数
     */
    private Integer totalSyncCount;

    /**
     * 成功次数
     */
    private Integer successCount;

    /**
     * 失败次数
     */
    private Integer failCount;

    /**
     * 最后同步时间
     */
    private String lastSyncTime;

    /**
     * 统计详情
     */
    private Map<String, Object> statistics;
}
