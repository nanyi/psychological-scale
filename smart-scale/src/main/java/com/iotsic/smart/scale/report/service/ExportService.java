package com.iotsic.smart.scale.report.service;

import com.iotsic.smart.scale.report.dto.ReportDownloadResponse;
import com.iotsic.smart.scale.report.dto.ReportExportResponse;

public interface ExportService {

    ReportExportResponse exportWord(Long reportId, Long templateId, String watermark);

    ReportExportResponse exportPdf(Long reportId, Long templateId, String pageSize, String orientation, String watermark);

    ReportDownloadResponse getDownloadUrl(String filePath, Long userId);
}
