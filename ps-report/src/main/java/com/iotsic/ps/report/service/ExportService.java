package com.iotsic.ps.report.service;

import com.iotsic.ps.report.entity.Report;

import java.util.Map;

public interface ExportService {

    Map<String, Object> exportWord(Long reportId, Long templateId, String watermark);

    Map<String, Object> exportPdf(Long reportId, Long templateId, String pageSize, String orientation, String watermark);

    String getDownloadUrl(String filePath, Long userId);
}
