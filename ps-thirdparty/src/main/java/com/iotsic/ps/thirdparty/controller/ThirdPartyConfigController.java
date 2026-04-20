package com.iotsic.ps.thirdparty.controller;

import com.iotsic.ps.thirdparty.dto.ConnectionTestResponse;
import com.iotsic.ps.thirdparty.dto.ThirdPartyConfigCreateRequest;
import com.iotsic.ps.thirdparty.dto.ThirdPartyConfigUpdateRequest;
import com.iotsic.ps.thirdparty.entity.ThirdPartyConfig;
import com.iotsic.ps.thirdparty.service.ThirdPartyConfigService;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 第三方配置控制器
 * 负责第三方平台配置的CRUD和测试请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/thirdparty/config")
@RequiredArgsConstructor
public class ThirdPartyConfigController {

    private final ThirdPartyConfigService thirdPartyConfigService;

    /**
     * 创建第三方配置
     * 
     * @param request 配置创建请求
     * @return 配置信息
     */
    @PostMapping("/create")
    public RestResult<ThirdPartyConfig> createConfig(@RequestBody ThirdPartyConfigCreateRequest request) {
        ThirdPartyConfig config = thirdPartyConfigService.createConfig(request);
        return RestResult.success("配置创建成功", config);
    }

    /**
     * 更新第三方配置
     * 
     * @param id 配置ID
     * @param request 配置更新请求
     * @return 操作结果
     */
    @PutMapping("/update/{id}")
    public RestResult<Void> updateConfig(@PathVariable Long id, @RequestBody ThirdPartyConfigUpdateRequest request) {
        thirdPartyConfigService.updateConfig(id, request);
        return RestResult.success();
    }

    /**
     * 删除配置
     * 
     * @param id 配置ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public RestResult<Void> deleteConfig(@PathVariable Long id) {
        thirdPartyConfigService.deleteConfig(id);
        return RestResult.success();
    }

    /**
     * 根据ID获取配置
     * 
     * @param id 配置ID
     * @return 配置信息
     */
    @GetMapping("/detail/{id}")
    public RestResult<ThirdPartyConfig> getConfigById(@PathVariable Long id) {
        return RestResult.success(thirdPartyConfigService.getConfigById(id));
    }

    /**
     * 根据编码获取配置
     * 
     * @param code 配置编码
     * @return 配置信息
     */
    @GetMapping("/by-code/{code}")
    public RestResult<ThirdPartyConfig> getConfigByCode(@PathVariable String code) {
        return RestResult.success(thirdPartyConfigService.getConfigByCode(code));
    }

    /**
     * 获取所有启用的配置
     * 
     * @return 配置列表
     */
    @GetMapping("/active")
    public RestResult<List<ThirdPartyConfig>> getActiveConfigs() {
        return RestResult.success(thirdPartyConfigService.getActiveConfigs());
    }

    /**
     * 启用配置
     * 
     * @param id 配置ID
     * @return 操作结果
     */
    @PostMapping("/enable/{id}")
    public RestResult<Void> enableConfig(@PathVariable Long id) {
        thirdPartyConfigService.enableConfig(id);
        return RestResult.success();
    }

    /**
     * 禁用配置
     * 
     * @param id 配置ID
     * @return 操作结果
     */
    @PostMapping("/disable/{id}")
    public RestResult<Void> disableConfig(@PathVariable Long id) {
        thirdPartyConfigService.disableConfig(id);
        return RestResult.success();
    }

    /**
     * 测试连接
     * 
     * @param id 配置ID
     * @return 测试结果
     */
    @GetMapping("/test/{id}")
    public RestResult<ConnectionTestResponse> testConnection(@PathVariable Long id) {
        Map<String, Object> result = thirdPartyConfigService.testConnection(id);
        
        ConnectionTestResponse response = new ConnectionTestResponse();
        response.setSuccess((Boolean) result.get("success"));
        response.setMessage((String) result.get("message"));
        
        return RestResult.success(response);
    }
}
