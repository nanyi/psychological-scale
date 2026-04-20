package com.iotsic.ps.scale.report.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 报告下载响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ReportDownloadResponse {

    /**
     * 下载URL
     */
    private String downloadUrl;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
}