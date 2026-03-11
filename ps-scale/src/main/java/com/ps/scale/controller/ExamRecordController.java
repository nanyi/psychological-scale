package com.ps.scale.controller;

import com.ps.common.request.PageRequest;
import com.ps.common.response.PageResult;
import com.ps.common.result.RestResult;
import com.ps.core.entity.ExamAnswer;
import com.ps.core.entity.ExamRecord;
import com.ps.scale.service.ExamRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/exam-record")
@RequiredArgsConstructor
public class ExamRecordController {

    private final ExamRecordService examRecordService;

    @GetMapping("/{id}")
    public RestResult<ExamRecord> getRecordById(@PathVariable Long id) {
        return RestResult.success(examRecordService.getRecordById(id));
    }

    @GetMapping("/{id}/answers")
    public RestResult<List<ExamAnswer>> getRecordAnswers(@PathVariable Long id) {
        return RestResult.success(examRecordService.getRecordAnswers(id));
    }

    @GetMapping("/{id}/detail")
    public RestResult<Map<String, Object>> getRecordDetail(@PathVariable Long id) {
        return RestResult.success(examRecordService.getRecordDetail(id));
    }

    @GetMapping("/list")
    public RestResult<PageResult<ExamRecord>> getRecordList(
            PageRequest request,
            @RequestParam(required = false) Map<String, Object> params) {
        return RestResult.success(examRecordService.getRecordList(request, params));
    }

    @GetMapping("/statistics")
    public RestResult<Map<String, Object>> getRecordStatistics(@RequestParam Long userId) {
        return RestResult.success(examRecordService.getRecordStatistics(userId));
    }

    @DeleteMapping("/{id}")
    public RestResult<Void> deleteRecord(@PathVariable Long id) {
        examRecordService.deleteRecord(id);
        return RestResult.success();
    }

    @GetMapping("/{id}/export")
    public RestResult<Void> exportRecord(@PathVariable Long id) {
        examRecordService.exportRecord(id);
        return RestResult.success();
    }
}
