package com.iotsic.ps.scale.thirdparty.controller;

import com.iotsic.ps.thirdparty.dto.SyncResultResponse;
import com.iotsic.ps.thirdparty.dto.SyncStatisticsResponse;
import com.iotsic.ps.thirdparty.entity.SyncLog;
import com.iotsic.ps.thirdparty.service.ScaleSyncService;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/thirdparty/sync")
@RequiredArgsConstructor
public class ScaleSyncController {

    private final ScaleSyncService scaleSyncService;

    @PostMapping("/scales/{configId}")
    public RestResult<SyncResultResponse> syncScales(@PathVariable Long configId) {
        SyncResultResponse result = scaleSyncService.syncScalesFromPlatform(configId);
        return RestResult.success("量表同步成功", result);
    }

    @PostMapping("/scale/{configId}/{externalScaleId}")
    public RestResult<SyncResultResponse> syncSingleScale(
            @PathVariable Long configId,
            @PathVariable String externalScaleId) {
        SyncResultResponse result = scaleSyncService.syncSingleScale(configId, externalScaleId);
        return RestResult.success("量表同步成功", result);
    }

    @GetMapping("/logs")
    public RestResult<List<SyncLog>> getSyncLogs(
            @RequestParam(value = "configId", required = false) Long configId,
            @RequestParam(value = "syncType", required = false) Integer syncType) {
        return RestResult.success(scaleSyncService.getSyncLogs(configId, syncType));
    }

    @GetMapping("/statistics/{configId}")
    public RestResult<SyncStatisticsResponse> getSyncStatistics(@PathVariable Long configId) {
        return RestResult.success(scaleSyncService.getSyncStatistics(configId));
    }
}
