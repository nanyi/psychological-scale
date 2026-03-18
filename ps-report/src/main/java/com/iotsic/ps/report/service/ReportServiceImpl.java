package com.iotsic.ps.report.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.utils.json.JsonUtils;
import com.iotsic.ps.core.entity.ExamRecord;
import com.iotsic.ps.core.entity.Report;
import com.iotsic.ps.report.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportTemplateService reportTemplateService;
    private final ReportMapper reportMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Report generateReport(Long taskId, Long templateId) {
        ExamRecord examRecord = getExamRecord(taskId);
        if (examRecord == null) {
            throw new BusinessException("测评记录不存在");
        }

        Report report = new Report();
        report.setReportNo(generateReportNo());
        report.setTaskId(taskId);
        report.setUserId(examRecord.getUserId());
        report.setScaleId(examRecord.getScaleId());
        report.setScaleName("量表" + examRecord.getScaleId());
        if (examRecord.getScore() != null) {
            report.setTotalScore(examRecord.getScore());
        } else if (examRecord.getTotalScore() != null) {
            report.setTotalScore(BigDecimal.valueOf(examRecord.getTotalScore()));
        }
        report.setStatus(0);
        report.setSourceType(1);
        report.setCreateTime(LocalDateTime.now());
        report.setUpdateTime(LocalDateTime.now());

        calculateDimensionScores(report, examRecord);
        determineResultLevel(report);
        generateConclusionAndSuggestions(report);

        if (templateId != null) {
            applyTemplate(report, templateId);
        } else {
            applyDefaultTemplate(report);
        }

        report.setStatus(1);
        report.setGenerateTime(LocalDateTime.now());

        reportMapper.insert(report);
        return report;
    }

    private ExamRecord getExamRecord(Long taskId) {
        return null;
    }

    private String generateReportNo() {
        return "RPT" + System.currentTimeMillis();
    }

    private void calculateDimensionScores(Report report, ExamRecord examRecord) {
        Map<String, Object> dimensionScores = new HashMap<>();
        dimensionScores.put("totalScore", report.getTotalScore());
        report.setDimensionScores(JsonUtils.toJson(dimensionScores));
    }

    private void determineResultLevel(Report report) {
        BigDecimal score = report.getTotalScore();
        if (score == null) {
            report.setResultLevel("未评分");
            return;
        }

        if (score.compareTo(new BigDecimal("50")) < 0) {
            report.setResultLevel("正常");
        } else if (score.compareTo(new BigDecimal("100")) < 0) {
            report.setResultLevel("轻度异常");
        } else if (score.compareTo(new BigDecimal("150")) < 0) {
            report.setResultLevel("中度异常");
        } else if (score.compareTo(new BigDecimal("200")) < 0) {
            report.setResultLevel("重度异常");
        } else {
            report.setResultLevel("严重异常");
        }
    }

    private void generateConclusionAndSuggestions(Report report) {
        String level = report.getResultLevel();
        String conclusion = "您的测评结果显示" + level + "。";
        
        List<String> suggestions = new ArrayList<>();
        switch (level) {
            case "正常":
                suggestions.add("继续保持良好的生活习惯");
                suggestions.add("适当进行体育锻炼");
                break;
            case "轻度异常":
                suggestions.add("建议关注自身心理状态");
                suggestions.add("适当调整工作和生活节奏");
                suggestions.add("可考虑咨询心理专业人士");
                break;
            case "中度异常":
                suggestions.add("建议尽快咨询心理医生");
                suggestions.add("适当休息调整");
                suggestions.add("寻求专业帮助");
                break;
            default:
                suggestions.add("建议立即寻求专业心理帮助");
                suggestions.add("联系心理咨询师或精神科医生");
        }

        report.setConclusion(conclusion);
        report.setSuggestions(JsonUtils.toJson(suggestions));
    }

    private void applyTemplate(Report report, Long templateId) {
        var template = reportTemplateService.getTemplateById(templateId);
        if (template != null) {
            String content = template.getTemplateContent();
            content = replaceVariables(content, report);
            report.setReportContent(content);
        }
    }

    private void applyDefaultTemplate(Report report) {
        var template = reportTemplateService.getDefaultTemplate(report.getScaleId());
        if (template != null) {
            String content = template.getTemplateContent();
            content = replaceVariables(content, report);
            report.setReportContent(content);
        } else {
            report.setReportContent(generateDefaultContent(report));
        }
    }

    private String replaceVariables(String content, Report report) {
        content = content.replace("$USER_NAME$", getUserName(report.getUserId()));
        content = content.replace("$SCALE_NAME$", report.getScaleName());
        content = content.replace("$TOTAL_SCORE$", String.valueOf(report.getTotalScore()));
        content = content.replace("$RESULT_LEVEL$", report.getResultLevel());
        content = content.replace("$ASSESS_DATE$", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        content = content.replace("$CONCLUSION$", report.getConclusion());
        return content;
    }

    private String getUserName(Long userId) {
        return "用户";
    }

    private String generateDefaultContent(Report report) {
        Map<String, Object> content = new HashMap<>();
        content.put("scaleName", report.getScaleName());
        content.put("totalScore", report.getTotalScore());
        content.put("resultLevel", report.getResultLevel());
        content.put("conclusion", report.getConclusion());
        content.put("suggestions", JsonUtils.fromJson(report.getSuggestions(), List.class));
        content.put("generateTime", report.getGenerateTime());
        return JsonUtils.toJson(content);
    }

    @Override
    public Report getReportByTaskId(Long taskId) {
        return reportMapper.selectOne(new LambdaQueryWrapper<Report>()
                .eq(Report::getTaskId, taskId)
                .orderByDesc(Report::getCreateTime)
                .last("LIMIT 1"));
    }

    @Override
    public PageResult<Report> getReportList(PageRequest request, Long userId, Long scaleId, Integer status) {
        Page<Report> page = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            wrapper.eq(Report::getUserId, userId);
        }
        if (scaleId != null) {
            wrapper.eq(Report::getScaleId, scaleId);
        }
        if (status != null) {
            wrapper.eq(Report::getStatus, status);
        }
        wrapper.orderByDesc(Report::getCreateTime);
        IPage<Report> result = reportMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal());
    }

    @Override
    public Report getReportDetail(Long reportId) {
        return reportMapper.selectById(reportId);
    }

    @Override
    public void updateReportStatus(Long reportId, Integer status) {
        Report report = new Report();
        report.setId(reportId);
        report.setStatus(status);
        report.setUpdateTime(LocalDateTime.now());
        reportMapper.updateById(report);
    }
}
