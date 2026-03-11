package com.iotsic.ps.thirdparty.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.thirdparty.entity.ThirdPartyConfig;
import com.iotsic.ps.thirdparty.service.ThirdPartyConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/thirdparty/config")
@RequiredArgsConstructor
public class ThirdPartyConfigController {

    private final ThirdPartyConfigService thirdPartyConfigService;

    @PostMapping("/create")
    public RestResult<ThirdPartyConfig> createConfig(@RequestBody Map<String, Object> params) {
        ThirdPartyConfig config = thirdPartyConfigService.createConfig(params);
        return RestResult.success("配置创建成功", config);
    }

    @PutMapping("/{id}")
    public RestResult<Void> updateConfig(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        thirdPartyConfigService.updateConfig(id, params);
        return RestResult.success();
    }

    @DeleteMapping("/{id}")
    public RestResult<Void> deleteConfig(@PathVariable Long id) {
        thirdPartyConfigService.deleteConfig(id);
        return RestResult.success();
    }

    @GetMapping("/{id}")
    public RestResult<ThirdPartyConfig> getConfigById(@PathVariable Long id) {
        return RestResult.success(thirdPartyConfigService.getConfigById(id));
    }

    @GetMapping("/code/{code}")
    public RestResult<ThirdPartyConfig> getConfigByCode(@PathVariable String code) {
        return RestResult.success(thirdPartyConfigService.getConfigByCode(code));
    }

    @GetMapping("/active")
    public RestResult<List<ThirdPartyConfig>> getActiveConfigs() {
        return RestResult.success(thirdPartyConfigService.getActiveConfigs());
    }

    @PostMapping("/{id}/enable")
    public RestResult<Void> enableConfig(@PathVariable Long id) {
        thirdPartyConfigService.enableConfig(id);
        return RestResult.success();
    }

    @PostMapping("/{id}/disable")
    public RestResult<Void> disableConfig(@PathVariable Long id) {
        thirdPartyConfigService.disableConfig(id);
        return RestResult.success();
    }

    @GetMapping("/{id}/test")
    public RestResult<Map<String, Object>> testConnection(@PathVariable Long id) {
        return RestResult.success(thirdPartyConfigService.testConnection(id));
    }
}
