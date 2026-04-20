package com.iotsic.ps.scale.report.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 报告导出响应DTO
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@Data
public class ReportExportResponse {

    /**
     * 下载链接
     */
    private String downloadUrl;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 链接过期时间
     */
    private LocalDateTime expireTime;
}