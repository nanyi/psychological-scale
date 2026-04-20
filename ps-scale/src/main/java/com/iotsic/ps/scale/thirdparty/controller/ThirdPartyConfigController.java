package com.iotsic.ps.scale.thirdparty.controller;

import com.iotsic.ps.scale.thirdparty.service.ScaleSyncService;
import com.iotsic.ps.scale.thirdparty.service.ThirdPartyConfigService;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 第三方配置控制器
 * 负责第三方平台配置的CRUD请求
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ThirdPartyConfigController {

    private final ThirdPartyConfigService thirdPartyConfigService;
    private final ScaleSyncService scaleSyncService;

    @PostMapping
    public RestResult<?> createConfig(@RequestBody Object config) {
        return RestResult.success();
    }

    @PutMapping
    public RestResult<?> updateConfig(@RequestBody Object config) {
        return RestResult.success();
    }

    @GetMapping("/list")
    public RestResult<?> getConfigList() {
        return RestResult.success();
    }

    @GetMapping("/{configId}")
    public RestResult<?> getConfigById(@PathVariable Long configId) {
        return RestResult.success(thirdPartyConfigService.getConfigById(configId));
    }

    @GetMapping("/code/{platformCode}")
    public RestResult<?> getConfigByCode(@PathVariable String platformCode) {
        return RestResult.success(thirdPartyConfigService.getConfigByCode(platformCode));
    }

    @PostMapping("/{configId}/sync")
    public RestResult<?> syncScales(@PathVariable Long configId) {
        scaleSyncService.syncScalesFromPlatform(configId);
        return RestResult.success();
    }
}