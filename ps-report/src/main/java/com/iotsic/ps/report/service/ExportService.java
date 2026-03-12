package com.iotsic.ps.report.service;

import com.iotsic.ps.report.dto.ReportDownloadResponse;
import com.iotsic.ps.report.dto.ReportExportResponse;
import com.iotsic.ps.report.entity.Report;

import java.util.Map;

public interface ExportService {

    ReportExportResponse exportWord(Long reportId, Long templateId, String watermark);

    ReportExportResponse exportPdf(Long reportId, Long templateId, String pageSize, String orientation, String watermark);

    ReportDownloadResponse getDownloadUrl(String filePath, Long userId);
}
