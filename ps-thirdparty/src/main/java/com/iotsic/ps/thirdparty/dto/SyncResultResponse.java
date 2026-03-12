package com.iotsic.ps.thirdparty.dto;

import lombok.Data;

import java.util.List;

/**
 * 同步结果响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class SyncResultResponse {

    /**
     * 成功数量
     */
    private Integer successCount;

    /**
     * 失败数量
     */
    private Integer failCount;

    /**
     * 量表ID列表
     */
    private List<Long> scaleIds;

    /**
     * 错误信息
     */
    private String errorMessage;
}
