package com.iotsic.ps.scale.thirdparty.service;

import com.iotsic.ps.scale.thirdparty.dto.PlatformAnswerResponse;
import com.iotsic.ps.scale.thirdparty.dto.PlatformQuestionsResponse;
import com.iotsic.ps.scale.thirdparty.dto.PlatformReportResponse;
import com.iotsic.ps.scale.thirdparty.entity.ThirdPartyConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThirdPartyApiServiceImpl implements ThirdPartyApiService {

    private final ThirdPartyConfigService thirdPartyConfigService;

    @Override
    public PlatformQuestionsResponse getQuestionsFromPlatform(Long configId, String externalScaleId) {
        ThirdPartyConfig config = thirdPartyConfigService.getConfigById(configId);
        
        log.info("从第三方平台获取题目: platformCode={}, externalScaleId={}", 
                config.getPlatformCode(), externalScaleId);
        
        List<Map<String, Object>> questions = new ArrayList<>();
        
        Map<String, Object> question1 = new HashMap<>();
        question1.put("questionId", "Q001");
        question1.put("questionText", "我经常感到紧张和担心");
        question1.put("questionType", 1);
        question1.put("isRequired", 1);
        
        List<Map<String, Object>> options1 = new ArrayList<>();
        options1.add(Map.of("value", "1", "text", "完全不符合"));
        options1.add(Map.of("value", "2", "text", "不符合"));
        options1.add(Map.of("value", "3", "text", "一般"));
        options1.add(Map.of("value", "4", "text", "符合"));
        options1.add(Map.of("value", "5", "text", "完全符合"));
        question1.put("options", options1);
        
        questions.add(question1);
        
        Map<String, Object> question2 = new HashMap<>();
        question2.put("questionId", "Q002");
        question2.put("questionText", "我能够以平静的心态面对压力");
        question2.put("questionType", 1);
        question2.put("isRequired", 1);
        question2.put("isReverse", 1);
        question2.put("options", options1);
        
        questions.add(question2);
        
        PlatformQuestionsResponse response = new PlatformQuestionsResponse();
        response.setPlatformCode(config.getPlatformCode());
        response.setExternalScaleId(externalScaleId);
        response.setScaleName("第三方量表-" + externalScaleId);
        response.setQuestionCount(questions.size());
        response.setQuestions(questions);
        
        return response;
    }

    @Override
    public PlatformAnswerResponse submitAnswersToPlatform(Long configId, Map<String, Object> params) {
        ThirdPartyConfig config = thirdPartyConfigService.getConfigById(configId);
        
        String externalScaleId = (String) params.get("externalScaleId");
        Map<Long, String> answers = (Map<Long, String>) params.get("answers");
        
        log.info("提交答案到第三方平台: platformCode={}, externalScaleId={}, answers={}", 
                config.getPlatformCode(), externalScaleId, answers);
        
        String externalRecordId = "REC" + System.currentTimeMillis();
        
        PlatformAnswerResponse response = new PlatformAnswerResponse();
        response.setExternalRecordId(externalRecordId);
        response.setSubmitStatus("success");
        response.setSubmitTime(LocalDateTime.now());
        
        return response;
    }

    @Override
    public PlatformReportResponse getReportFromPlatform(Long configId, String externalRecordId) {
        ThirdPartyConfig config = thirdPartyConfigService.getConfigById(configId);
        
        log.info("从第三方平台获取报告: platformCode={}, externalRecordId={}", 
                config.getPlatformCode(), externalRecordId);
        
        PlatformReportResponse response = new PlatformReportResponse();
        response.setExternalRecordId(externalRecordId);
        response.setReportStatus("completed");
        response.setReportUrl("https://thirdparty.com/report/" + externalRecordId);
        response.setReportContent("这是第三方平台的测评报告内容");
        
        return response;
    }
}
