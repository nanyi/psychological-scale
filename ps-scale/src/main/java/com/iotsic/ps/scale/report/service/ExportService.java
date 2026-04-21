package com.iotsic.ps.scale.report.service;

import com.iotsic.ps.report.dto.ReportDownloadResponse;
import com.iotsic.ps.report.dto.ReportExportResponse;

public interface ExportService {

    ReportExportResponse exportWord(Long reportId, Long templateId, String watermark);

    ReportExportResponse exportPdf(Long reportId, Long templateId, String pageSize, String orientation, String watermark);

    ReportDownloadResponse getDownloadUrl(String filePath, Long userId);
}
