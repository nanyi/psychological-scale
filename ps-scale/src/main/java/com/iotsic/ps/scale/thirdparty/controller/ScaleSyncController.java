package com.iotsic.ps.scale.thirdparty.controller;

import com.iotsic.ps.scale.thirdparty.service.ScaleSyncService;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 量表同步控制器
 * 负责第三方量表同步的请求
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class ScaleSyncController {

    private final ScaleSyncService scaleSyncService;

    @PostMapping("/scales/{configId}")
    public RestResult<?> syncScales(@PathVariable Long configId) {
        scaleSyncService.syncScalesFromPlatform(configId);
        return RestResult.success();
    }

    @PostMapping("/questions")
    public RestResult<?> syncQuestions(
            @RequestParam String platformCode,
            @RequestParam String scaleId) {
        scaleSyncService.syncScaleQuestions(platformCode, scaleId);
        return RestResult.success();
    }

    @PostMapping("/schedule/{configId}")
    public RestResult<?> scheduleSync(
            @PathVariable Long configId,
            @RequestParam Integer intervalMinutes) {
        scaleSyncService.scheduleSyncTask(configId, intervalMinutes);
        return RestResult.success();
    }
}