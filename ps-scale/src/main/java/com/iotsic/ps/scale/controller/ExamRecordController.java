package com.iotsic.ps.scale.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.scale.dto.RecordDetailResponse;
import com.iotsic.ps.scale.dto.RecordStatisticsResponse;
import com.iotsic.ps.scale.dto.ExamRecordListRequest;
import com.iotsic.ps.core.entity.ExamAnswer;
import com.iotsic.ps.core.entity.ExamRecord;
import com.iotsic.ps.scale.service.ExamRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测评记录控制器
 * 负责测评记录的查询、统计等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/exam-record")
@RequiredArgsConstructor
public class ExamRecordController {

    private final ExamRecordService examRecordService;

    /**
     * 根据ID获取测评记录
     * 
     * @param id 记录ID
     * @return 测评记录
     */
    @GetMapping("/detail/{id}")
    public RestResult<ExamRecord> getRecordById(@PathVariable Long id) {
        return RestResult.success(examRecordService.getRecordById(id));
    }

    /**
     * 获取记录的所有答案
     * 
     * @param id 记录ID
     * @return 答案列表
     */
    @GetMapping("/answers/{id}")
    public RestResult<List<ExamAnswer>> getRecordAnswers(@PathVariable Long id) {
        return RestResult.success(examRecordService.getRecordAnswers(id));
    }

    /**
     * 获取记录详情
     * 
     * @param id 记录ID
     * @return 记录详情
     */
    @GetMapping("/info/{id}")
    public RestResult<RecordDetailResponse> getRecordDetail(@PathVariable Long id) {
        RecordDetailResponse response = examRecordService.getRecordDetail(id);
        return RestResult.success(response);
    }

    /**
     * 获取记录列表
     * 
     * @param request 分页请求
     * @param examRecordListRequest 查询参数
     * @return 记录分页列表
     */
    @GetMapping("/list")
    public RestResult<PageResult<ExamRecord>> getRecordList(
            PageRequest request,
            ExamRecordListRequest examRecordListRequest) {
        return RestResult.success(examRecordService.getRecordList(request, examRecordListRequest));
    }

    /**
     * 获取记录统计
     * 
     * @param userId 用户ID
     * @return 统计数据
     */
    @GetMapping("/statistics")
    public RestResult<RecordStatisticsResponse> getRecordStatistics(@RequestParam Long userId) {
        RecordStatisticsResponse response = examRecordService.getRecordStatistics(userId);
        return RestResult.success(response);
    }

    /**
     * 删除记录
     * 
     * @param id 记录ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public RestResult<Void> deleteRecord(@PathVariable Long id) {
        examRecordService.deleteRecord(id);
        return RestResult.success();
    }

    /**
     * 导出记录
     * 
     * @param id 记录ID
     * @return 操作结果
     */
    @GetMapping("/export/{id}")
    public RestResult<Void> exportRecord(@PathVariable Long id) {
        examRecordService.exportRecord(id);
        return RestResult.success();
    }
}
