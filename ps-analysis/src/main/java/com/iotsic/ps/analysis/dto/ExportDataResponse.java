package com.iotsic.ps.analysis.dto;

import lombok.Data;

/**
 * 导出数据响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ExportDataResponse {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件访问URL
     */
    private String fileUrl;

    /**
     * 导出记录数
     */
    private Integer recordCount;

    /**
     * 导出时间
     */
    private String exportTime;
}
