package com.iotsic.ps.thirdparty.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.thirdparty.dto.AnswerSubmitRequest;
import com.iotsic.ps.thirdparty.entity.ThirdPartyCallback;
import com.iotsic.ps.thirdparty.service.CallbackService;
import com.iotsic.ps.thirdparty.service.ThirdPartyApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 第三方API控制器
 * 负责第三方平台的题目、答案、报告等接口请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/thirdparty")
@RequiredArgsConstructor
public class ThirdPartyApiController {

    private final ThirdPartyApiService thirdPartyApiService;
    private final CallbackService callbackService;

    /**
     * 获取第三方平台题目
     * 
     * @param configId 配置ID
     * @param externalScaleId 外部量表ID
     * @return 题目数据
     */
    @GetMapping("/questions/by-config/{configId}/scale/{externalScaleId}")
    public RestResult<Map<String, Object>> getQuestions(
            @PathVariable Long configId,
            @PathVariable String externalScaleId) {
        return RestResult.success(thirdPartyApiService.getQuestionsFromPlatform(configId, externalScaleId));
    }

    /**
     * 提交答案到第三方平台
     * 
     * @param configId 配置ID
     * @param request 答案提交请求
     * @return 提交结果
     */
    @PostMapping("/answers/by-config/{configId}")
    public RestResult<Map<String, Object>> submitAnswers(
            @PathVariable Long configId,
            @RequestBody AnswerSubmitRequest request) {
        return RestResult.success(thirdPartyApiService.submitAnswersToPlatform(configId, request.getAnswers()));
    }

    /**
     * 获取第三方平台报告
     * 
     * @param configId 配置ID
     * @param externalRecordId 外部记录ID
     * @return 报告数据
     */
    @GetMapping("/report/by-config/{configId}/record/{externalRecordId}")
    public RestResult<Map<String, Object>> getReport(
            @PathVariable Long configId,
            @PathVariable String externalRecordId) {
        return RestResult.success(thirdPartyApiService.getReportFromPlatform(configId, externalRecordId));
    }

    /**
     * 处理第三方平台回调
     * 
     * @param configId 配置ID
     * @param params 回调参数
     * @return 处理结果
     */
    @PostMapping("/callback/by-config/{configId}")
    public RestResult<Map<String, Object>> handleCallback(
            @PathVariable Long configId,
            @RequestBody Map<String, Object> params) {
        log.info("收到第三方平台回调: configId={}, params={}", configId, params);
        return RestResult.success(callbackService.handleReportCallback(configId, params));
    }

    /**
     * 根据ID获取回调记录
     * 
     * @param id 回调ID
     * @return 回调记录
     */
    @GetMapping("/callback/detail/{id}")
    public RestResult<ThirdPartyCallback> getCallbackById(@PathVariable Long id) {
        return RestResult.success(callbackService.getCallbackById(id));
    }

    /**
     * 根据外部记录ID获取回调记录
     * 
     * @param externalRecordId 外部记录ID
     * @return 回调记录列表
     */
    @GetMapping("/callback/by-record/{externalRecordId}")
    public RestResult<List<ThirdPartyCallback>> getCallbacksByRecordId(@PathVariable String externalRecordId) {
        return RestResult.success(callbackService.getCallbacksByRecordId(externalRecordId));
    }

    /**
     * 重试回调
     * 
     * @param id 回调ID
     * @return 操作结果
     */
    @PostMapping("/callback/retry/{id}")
    public RestResult<Void> retryCallback(@PathVariable Long id) {
        callbackService.retryCallback(id);
        return RestResult.success();
    }
}
