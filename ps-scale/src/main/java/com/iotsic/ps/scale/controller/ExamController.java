package com.iotsic.ps.scale.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.core.entity.ExamRecord;
import com.iotsic.ps.scale.dto.AnswerSaveRequest;
import com.iotsic.ps.scale.dto.ExamProgressResponse;
import com.iotsic.ps.scale.dto.ExamStartRequest;
import com.iotsic.ps.scale.dto.ExamSubmitResponse;
import com.iotsic.ps.scale.service.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 测评控制器
 * 负责测评的开始、答题、提交等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/exam")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    /**
     * 开始测评
     * 
     * @param request 测评开始请求
     * @return 测评记录
     */
    @PostMapping("/start")
    public RestResult<ExamRecord> startExam(@RequestBody ExamStartRequest request) {
        ExamRecord record = examService.startExam(
                request.getUserId(),
                request.getScaleId(),
                request.getIpAddress(),
                request.getDeviceInfo()
        );
        return RestResult.success("测评已开始", record);
    }

    /**
     * 根据ID获取测评记录
     * 
     * @param id 记录ID
     * @return 测评记录
     */
    @GetMapping("/record/detail/{id}")
    public RestResult<ExamRecord> getExamRecordById(@PathVariable Long id) {
        return RestResult.success(examService.getExamRecordById(id));
    }

    /**
     * 根据记录编号获取测评记录
     * 
     * @param recordNo 记录编号
     * @return 测评记录
     */
    @GetMapping("/record/by-no/{recordNo}")
    public RestResult<ExamRecord> getExamRecordByNo(@PathVariable String recordNo) {
        return RestResult.success(examService.getExamRecordByNo(recordNo));
    }

    /**
     * 保存答题答案
     * 
     * @param request 答案保存请求
     * @return 操作结果
     */
    @PostMapping("/answer/save")
    public RestResult<Void> saveAnswer(@RequestBody AnswerSaveRequest request) {
        examService.saveAnswer(request.getRecordId(), request.getAnswers());
        return RestResult.success();
    }

    /**
     * 提交测评
     * 
     * @param recordId 记录ID
     * @return 提交结果
     */
    @PostMapping("/submit/{recordId}")
    public RestResult<ExamSubmitResponse> submitExam(@PathVariable Long recordId) {
        Map<String, Object> result = examService.submitExam(recordId);
        
        ExamSubmitResponse response = new ExamSubmitResponse();
        response.setRecordId(recordId);
        response.setStatus((String) result.get("status"));
        response.setTotalQuestions((Integer) result.get("totalQuestions"));
        response.setAnsweredCount((Integer) result.get("answeredCount"));
        
        return RestResult.success("测评提交成功", response);
    }

    /**
     * 暂停测评
     * 
     * @param recordId 记录ID
     * @return 操作结果
     */
    @PostMapping("/pause/{recordId}")
    public RestResult<Void> pauseExam(@PathVariable Long recordId) {
        examService.pauseExam(recordId);
        return RestResult.success();
    }

    /**
     * 恢复测评
     * 
     * @param recordId 记录ID
     * @return 操作结果
     */
    @PostMapping("/resume/{recordId}")
    public RestResult<Void> resumeExam(@PathVariable Long recordId) {
        examService.resumeExam(recordId);
        return RestResult.success();
    }

    /**
     * 获取用户测评记录列表
     * 
     * @param request 分页请求
     * @param userId 用户ID
     * @return 测评记录分页列表
     */
    @GetMapping("/list")
    public RestResult<PageResult<ExamRecord>> getUserExamRecords(
            PageRequest request,
            @RequestParam Long userId) {
        return RestResult.success(examService.getUserExamRecords(request, userId));
    }

    /**
     * 获取最新测评记录
     * 
     * @param userId 用户ID
     * @param scaleId 量表ID
     * @return 测评记录
     */
    @GetMapping("/latest")
    public RestResult<ExamRecord> getLatestExamRecord(
            @RequestParam Long userId,
            @RequestParam Long scaleId) {
        return RestResult.success(examService.getLatestExamRecord(userId, scaleId));
    }

    /**
     * 获取测评进度
     * 
     * @param recordId 记录ID
     * @return 测评进度
     */
    @GetMapping("/progress/detail/{recordId}")
    public RestResult<ExamProgressResponse> getExamProgress(@PathVariable Long recordId) {
        Map<String, Object> result = examService.getExamProgress(recordId);
        
        ExamProgressResponse response = new ExamProgressResponse();
        response.setRecordId(recordId);
        response.setTotalQuestions((Integer) result.get("totalQuestions"));
        response.setAnsweredCount((Integer) result.get("answeredCount"));
        response.setCurrentIndex((Integer) result.get("currentIndex"));
        response.setRemainingTime((Integer) result.get("remainingTime"));
        response.setStatus((String) result.get("status"));
        
        return RestResult.success(response);
    }
}
