package com.iotsic.ps.thirdparty.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.thirdparty.entity.SyncLog;
import com.iotsic.ps.thirdparty.service.ScaleSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/thirdparty/sync")
@RequiredArgsConstructor
public class ScaleSyncController {

    private final ScaleSyncService scaleSyncService;

    @PostMapping("/scales/{configId}")
    public RestResult<Map<String, Object>> syncScales(@PathVariable Long configId) {
        Map<String, Object> result = scaleSyncService.syncScalesFromPlatform(configId);
        return RestResult.success("量表同步成功", result);
    }

    @PostMapping("/scale/{configId}/{externalScaleId}")
    public RestResult<Map<String, Object>> syncSingleScale(
            @PathVariable Long configId,
            @PathVariable String externalScaleId) {
        Map<String, Object> result = scaleSyncService.syncSingleScale(configId, externalScaleId);
        return RestResult.success("量表同步成功", result);
    }

    @GetMapping("/logs")
    public RestResult<List<SyncLog>> getSyncLogs(
            @RequestParam(required = false) Long configId,
            @RequestParam(required = false) Integer syncType) {
        return RestResult.success(scaleSyncService.getSyncLogs(configId, syncType));
    }

    @GetMapping("/statistics/{configId}")
    public RestResult<Map<String, Object>> getSyncStatistics(@PathVariable Long configId) {
        return RestResult.success(scaleSyncService.getSyncStatistics(configId));
    }
}
