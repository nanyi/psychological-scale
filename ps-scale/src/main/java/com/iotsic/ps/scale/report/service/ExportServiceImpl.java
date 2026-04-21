package com.iotsic.ps.report.service;

import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.core.entity.Report;
import com.iotsic.ps.report.dto.ReportDownloadResponse;
import com.iotsic.ps.report.dto.ReportExportResponse;
import com.iotsic.ps.report.entity.ReportExport;
import com.iotsic.ps.report.mapper.ReportExportMapper;
import com.iotsic.smart.framework.common.utils.json.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText(report.getScaleName() + "测评报告");
            titleRun.setFontSize(20);
            titleRun.setBold(true);

            document.createParagraph();

            XWPFParagraph baseInfo = document.createParagraph();
            baseInfo.createRun().setText("【基本信息】");
            baseInfo.createRun().addBreak();
            baseInfo.createRun().setText("姓名：" + getUserName(report.getUserId()));
            baseInfo.createRun().addBreak();
            baseInfo.createRun().setText("测评日期：" + (report.getGenerateTime() != null ? 
                report.getGenerateTime().format(DateTimeFormatter.ISO_DATE) : ""));
            baseInfo.createRun().addBreak();

            document.createParagraph();

            XWPFParagraph result = document.createParagraph();
            result.createRun().setText("【测评结果】");
            result.createRun().addBreak();
            result.createRun().setText("总分：" + (report.getTotalScore() != null ? report.getTotalScore().toString() : ""));
            result.createRun().addBreak();
            result.createRun().setText("结果等级：" + report.getResultLevel());

            document.createParagraph();

            XWPFParagraph conclusion = document.createParagraph();
            conclusion.createRun().setText("【综合结论】");
            conclusion.createRun().addBreak();
            conclusion.createRun().setText(report.getConclusion() != null ? report.getConclusion() : "");

            document.createParagraph();

            XWPFParagraph suggestions = document.createParagraph();
            suggestions.createRun().setText("【建议指导】");
            suggestions.createRun().addBreak();
            
            List<String> suggestionList = JsonUtils.parseArray(report.getSuggestions(), String.class);
            if (suggestionList != null) {
                int i = 1;
                for (String suggestion : suggestionList) {
                    suggestions.createRun().setText(i + ". " + suggestion);
                    suggestions.createRun().addBreak();
                    i++;
                }
            }

            document.createParagraph();

            XWPFParagraph note = document.createParagraph();
            note.createRun().setText("【报告说明】");
            note.createRun().addBreak();
            note.createRun().setText("本报告仅供参考，不作为诊断依据。");

            if (watermark != null && !watermark.isEmpty()) {
                addWatermark(document, watermark);
            }

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                document.write(out);
            }
        }
    }

    private void addWatermark(XWPFDocument document, String watermark) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                run.setText(run.getText(0) + " " + watermark, 0);
            }
        }
    }

    private void createPdfDocument(Report report, String filePath, String watermark) throws Exception {
        String htmlContent = generateHtmlContent(report, watermark);
        
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            out.write(htmlContent.getBytes("UTF-8"));
        }
    }

    private String generateHtmlContent(Report report, String watermark) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        html.append("<title>").append(report.getScaleName()).append("测评报告</title>");
        html.append("<style>");
        html.append("body { font-family: 'SimSun', serif; padding: 40px; }");
        html.append("h1 { text-align: center; font-size: 24px; }");
        html.append("h2 { font-size: 18px; margin-top: 20px; border-bottom: 1px solid #ccc; }");
        html.append(".info { margin: 10px 0; }");
        html.append(".watermark { position: fixed; opacity: 0.1; font-size: 60px; transform: rotate(45deg); }");
        html.append("</style></head><body>");
        
        if (watermark != null && !watermark.isEmpty()) {
            html.append("<div class='watermark'>").append(watermark).append("</div>");
        }
        
        html.append("<h1>").append(report.getScaleName()).append("测评报告</h1>");
        html.append("<h2>基本信息</h2>");
        html.append("<div class='info'>姓名：").append(getUserName(report.getUserId())).append("</div>");
        html.append("<div class='info'>测评日期：").append(report.getGenerateTime() != null ? 
            report.getGenerateTime().format(DateTimeFormatter.ISO_DATE) : "").append("</div>");
        
        html.append("<h2>测评结果</h2>");
        html.append("<div class='info'>总分：").append(report.getTotalScore() != null ? report.getTotalScore().toString() : "").append("</div>");
        html.append("<div class='info'>结果等级：").append(report.getResultLevel()).append("</div>");
        
        html.append("<h2>综合结论</h2>");
        html.append("<p>").append(report.getConclusion() != null ? report.getConclusion() : "").append("</p>");
        
        html.append("<h2>建议指导</h2><ul>");
        List<String> suggestionList = JsonUtils.parseArray(report.getSuggestions(), String.class);
        if (suggestionList != null) {
            for (String suggestion : suggestionList) {
                html.append("<li>").append(suggestion).append("</li>");
            }
        }
        html.append("</ul>");
        
        html.append("<h2>报告说明</h2>");
        html.append("<p>本报告仅供参考，不作为诊断依据。</p>");
        
        html.append("</body></html>");
        return html.toString();
    }

    private String generateFileName(String scaleName, String extension) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String name = scaleName != null ? scaleName.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]", "") : "report";
        return name + "_报告_" + timestamp + "." + extension;
    }

    private String getUserName(Long userId) {
        return "用户" + userId;
    }

    @Override
    public ReportDownloadResponse getDownloadUrl(String filePath, Long userId) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

        ReportDownloadResponse result = new ReportDownloadResponse();
        result.setDownloadUrl(urlPrefix + "/" + fileName);
        result.setFileName(fileName);
        result.setExpireTime(LocalDateTime.now().plusDays(7));
        return result;
    }
}
