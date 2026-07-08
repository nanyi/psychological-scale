package com.iotsic.smart.system.controller.admin;

import com.iotsic.smart.framework.common.result.RestResult;
import com.iotsic.smart.framework.security.annotation.RequirePermission;
import com.iotsic.smart.system.dto.SysConfigRequest;
import com.iotsic.smart.system.dto.SysConfigVO;
import com.iotsic.smart.system.service.SysConfigService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统配置管理 Controller
 *
 * @author Ryan
 * @since 2026-07-08
 */
@RestController
@RequestMapping("/api/system/config")
@RequiredArgsConstructor
public class SysConfigController {

    private final SysConfigService sysConfigService;

    /**
     * 获取所有配置
     */
    @GetMapping("/all")
    @RequirePermission("system:config:list")
    public RestResult<Map<String, Map<String, SysConfigVO>>> getAllConfig() {
        return RestResult.success(sysConfigService.getAllConfig());
    }

    /**
     * 获取指定分组的配置
     */
    @GetMapping("/{category}")
    @RequirePermission("system:config:list")
    public RestResult<List<SysConfigVO>> getConfigByCategory(@PathVariable String category) {
        return RestResult.success(sysConfigService.getConfigByCategory(category));
    }

    /**
     * 更新配置
     */
    @PutMapping("/{id}")
    @RequirePermission("system:config:update")
    public RestResult<Void> updateConfig(@PathVariable Long id, @RequestBody ConfigUpdateRequest request) {
        sysConfigService.updateConfig(id, request.getConfigValue());
        return RestResult.success();
    }

    /**
     * 新增配置
     */
    @PostMapping
    @RequirePermission("system:config:add")
    public RestResult<Void> addConfig(@RequestBody SysConfigRequest request) {
        sysConfigService.addConfig(request);
        return RestResult.success();
    }

    /**
     * 删除配置
     */
    @DeleteMapping("/{id}")
    @RequirePermission("system:config:delete")
    public RestResult<Void> deleteConfig(@PathVariable Long id) {
        sysConfigService.deleteConfig(id);
        return RestResult.success();
    }

    /**
     * 刷新配置缓存
     */
    @PostMapping("/cache/refresh")
    @RequirePermission("system:config:update")
    public RestResult<Void> refreshCache() {
        sysConfigService.refreshCache();
        return RestResult.success();
    }

    @Data
    public static class ConfigUpdateRequest {
        private String configValue;
    }
}
