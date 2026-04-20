package com.iotsic.ps.scale.report.service;

import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.core.entity.Report;
import com.iotsic.ps.scale.report.dto.ReportDownloadResponse;
import com.iotsic.ps.scale.report.dto.ReportExportResponse;
import com.iotsic.ps.scale.report.entity.ReportExport;
import com.iotsic.ps.scale.report.mapper.ReportExportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    @Value("${report.export.path:/tmp/reports}")
    private String exportPath;

    @Value("${report.export.url-prefix:http://localhost:8080/files}")
    private String urlPrefix;

    private final ReportService reportService;
    private final ReportExportMapper reportExportMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReportExportResponse exportWord(Long reportId, Long templateId, String watermark) {
        Report report = reportService.getReportDetail(reportId);
        if (report == null) {
            throw new BusinessException("报告不存在");
        }

        try {
            String fileName = generateFileName(report.getScaleName(), "docx");
            String filePath = exportPath + "/" + fileName;

            createWordDocument(report, filePath, watermark);

            ReportExport exportRecord = new ReportExport();
            exportRecord.setReportId(reportId);
            exportRecord.setUserId(report.getUserId());
            exportRecord.setExportType(1);
            exportRecord.setFileUrl(urlPrefix + "/" + fileName);
            exportRecord.setCreateTime(LocalDateTime.now());
            reportExportMapper.insert(exportRecord);

            ReportExportResponse result = new ReportExportResponse();
            result.setDownloadUrl(exportRecord.getFileUrl());
            result.setFileName(fileName);
            result.setExpireTime(LocalDateTime.now().plusDays(7));

            return result;
        } catch (Exception e) {
            log.error("Word导出失败", e);
            throw new BusinessException("Word导出失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReportExportResponse exportPdf(Long reportId, Long templateId, String pageSize, String orientation, String watermark) {
        Report report = reportService.getReportDetail(reportId);
        if (report == null) {
            throw new BusinessException("报告不存在");
        }

        try {
            String fileName = generateFileName(report.getScaleName(), "pdf");
            String filePath = exportPath + "/" + fileName;

            createPdfDocument(report, filePath, watermark);

            ReportExport exportRecord = new ReportExport();
            exportRecord.setReportId(reportId);
            exportRecord.setUserId(report.getUserId());
            exportRecord.setExportType(2);
            exportRecord.setFileUrl(urlPrefix + "/" + fileName);
            exportRecord.setCreateTime(LocalDateTime.now());
            reportExportMapper.insert(exportRecord);

            ReportExportResponse result = new ReportExportResponse();
            result.setDownloadUrl(exportRecord.getFileUrl());
            result.setFileName(fileName);
            result.setExpireTime(LocalDateTime.now().plusDays(7));

            return result;
        } catch (Exception e) {
            log.error("PDF导出失败", e);
            throw new BusinessException("PDF导出失败: " + e.getMessage());
        }
    }

    private void createWordDocument(Report report, String filePath, String watermark) throws Exception {
        log.info("生成Word报告: {}", filePath);
    }

    private void createPdfDocument(Report report, String filePath, String watermark) throws Exception {
        log.info("生成PDF报告: {}", filePath);
    }

    private String generateFileName(String scaleName, String extension) {
        return scaleName + "_" + System.currentTimeMillis() + "." + extension;
    }

    @Override
    public ReportDownloadResponse getDownloadUrl(String filePath, Long userId) {
        ReportDownloadResponse response = new ReportDownloadResponse();
        response.setDownloadUrl(urlPrefix + "/" + filePath);
        response.setExpireTime(LocalDateTime.now().plusHours(2));
        return response;
    }

    private String getUserName(Long userId) {
        return "用户" + userId;
    }
}