package com.iotsic.ps.thirdparty.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.thirdparty.entity.ThirdPartyCallback;
import com.iotsic.ps.thirdparty.service.CallbackService;
import com.iotsic.ps.thirdparty.service.ThirdPartyApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/thirdparty")
@RequiredArgsConstructor
public class ThirdPartyApiController {

    private final ThirdPartyApiService thirdPartyApiService;
    private final CallbackService callbackService;

    @GetMapping("/questions/{configId}/{externalScaleId}")
    public RestResult<Map<String, Object>> getQuestions(
            @PathVariable Long configId,
            @PathVariable String externalScaleId) {
        return RestResult.success(thirdPartyApiService.getQuestionsFromPlatform(configId, externalScaleId));
    }

    @PostMapping("/answers/{configId}")
    public RestResult<Map<String, Object>> submitAnswers(
            @PathVariable Long configId,
            @RequestBody Map<String, Object> params) {
        return RestResult.success(thirdPartyApiService.submitAnswersToPlatform(configId, params));
    }

    @GetMapping("/report/{configId}/{externalRecordId}")
    public RestResult<Map<String, Object>> getReport(
            @PathVariable Long configId,
            @PathVariable String externalRecordId) {
        return RestResult.success(thirdPartyApiService.getReportFromPlatform(configId, externalRecordId));
    }

    @PostMapping("/callback/{configId}")
    public RestResult<Map<String, Object>> handleCallback(
            @PathVariable Long configId,
            @RequestBody Map<String, Object> params) {
        log.info("收到第三方平台回调: configId={}, params={}", configId, params);
        return RestResult.success(callbackService.handleReportCallback(configId, params));
    }

    @GetMapping("/callback/{id}")
    public RestResult<ThirdPartyCallback> getCallbackById(@PathVariable Long id) {
        return RestResult.success(callbackService.getCallbackById(id));
    }

    @GetMapping("/callback/record/{externalRecordId}")
    public RestResult<List<ThirdPartyCallback>> getCallbacksByRecordId(@PathVariable String externalRecordId) {
        return RestResult.success(callbackService.getCallbacksByRecordId(externalRecordId));
    }

    @PostMapping("/callback/{id}/retry")
    public RestResult<Void> retryCallback(@PathVariable Long id) {
        callbackService.retryCallback(id);
        return RestResult.success();
    }
}
