package com.iotsic.ps.scale.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.core.entity.ExamRecord;
import com.iotsic.ps.scale.service.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/exam")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping("/start")
    public RestResult<ExamRecord> startExam(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        String ipAddress = (String) params.get("ipAddress");
        String deviceInfo = (String) params.get("deviceInfo");

        ExamRecord record = examService.startExam(userId, scaleId, ipAddress, deviceInfo);
        return RestResult.success("测评已开始", record);
    }

    @GetMapping("/record/{id}")
    public RestResult<ExamRecord> getExamRecordById(@PathVariable Long id) {
        return RestResult.success(examService.getExamRecordById(id));
    }

    @GetMapping("/record/no/{recordNo}")
    public RestResult<ExamRecord> getExamRecordByNo(@PathVariable String recordNo) {
        return RestResult.success(examService.getExamRecordByNo(recordNo));
    }

    @PostMapping("/answer/save")
    public RestResult<Void> saveAnswer(@RequestBody Map<String, Object> params) {
        Long recordId = Long.valueOf(params.get("recordId").toString());
        Map<Long, String> answers = (Map<Long, String>) params.get("answers");
        examService.saveAnswer(recordId, answers);
        return RestResult.success();
    }

    @PostMapping("/submit")
    public RestResult<Map<String, Object>> submitExam(@RequestBody Map<String, Object> params) {
        Long recordId = Long.valueOf(params.get("recordId").toString());
        Map<String, Object> result = examService.submitExam(recordId);
        return RestResult.success("测评提交成功", result);
    }

    @PostMapping("/pause")
    public RestResult<Void> pauseExam(@PathVariable Long recordId) {
        examService.pauseExam(recordId);
        return RestResult.success();
    }

    @PostMapping("/resume")
    public RestResult<Void> resumeExam(@PathVariable Long recordId) {
        examService.resumeExam(recordId);
        return RestResult.success();
    }

    @GetMapping("/list")
    public RestResult<PageResult<ExamRecord>> getUserExamRecords(
            PageRequest request,
            @RequestParam Long userId) {
        return RestResult.success(examService.getUserExamRecords(request, userId));
    }

    @GetMapping("/latest")
    public RestResult<ExamRecord> getLatestExamRecord(
            @RequestParam Long userId,
            @RequestParam Long scaleId) {
        return RestResult.success(examService.getLatestExamRecord(userId, scaleId));
    }

    @GetMapping("/progress/{recordId}")
    public RestResult<Map<String, Object>> getExamProgress(@PathVariable Long recordId) {
        return RestResult.success(examService.getExamProgress(recordId));
    }
}
