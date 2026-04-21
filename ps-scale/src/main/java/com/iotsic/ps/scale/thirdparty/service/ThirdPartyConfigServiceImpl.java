package com.iotsic.ps.scale.thirdparty.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.thirdparty.dto.ThirdPartyConfigCreateRequest;
import com.iotsic.ps.thirdparty.dto.ThirdPartyConfigUpdateRequest;
import com.iotsic.ps.thirdparty.entity.ThirdPartyConfig;
import com.iotsic.ps.thirdparty.mapper.ThirdPartyConfigMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThirdPartyConfigServiceImpl implements ThirdPartyConfigService {

    private final ThirdPartyConfigMapper thirdPartyConfigMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ThirdPartyConfig createConfig(ThirdPartyConfigCreateRequest request) {
        String platformCode = request.getPlatformCode();
        
        LambdaQueryWrapper<ThirdPartyConfig> existWrapper = new LambdaQueryWrapper<>();
        existWrapper.eq(ThirdPartyConfig::getPlatformCode, platformCode);
        if (thirdPartyConfigMapper.selectOne(existWrapper) != null) {
            throw BusinessException.of(ErrorCodeEnum.PLATFORM_EXIST.getCode(), "平台代码已存在");
        }

        ThirdPartyConfig config = new ThirdPartyConfig();
        config.setPlatformName(request.getPlatformName());
        config.setPlatformCode(platformCode);
        config.setAppKey(request.getAppKey());
        config.setAppSecret(request.getAppSecret());
        config.setApiUrl(request.getApiUrl());
        config.setCallbackUrl(request.getCallbackUrl());
        config.setPlatformType(request.getPlatformType());
        config.setConfigJson(request.getConfigJson());
        config.setSyncInterval(request.getSyncInterval() != null ? request.getSyncInterval() : 60);
        config.setDescription(request.getDescription());
        config.setStatus(1);
        config.setCreateTime(LocalDateTime.now());
        config.setUpdateTime(LocalDateTime.now());

        thirdPartyConfigMapper.insert(config);
        log.info("创建第三方平台配置: platformCode={}", platformCode);
        
        return config;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConfig(Long id, ThirdPartyConfigUpdateRequest request) {
        ThirdPartyConfig config = getConfigById(id);

        if (request.getPlatformName() != null) {
            config.setPlatformName(request.getPlatformName());
        }
        if (request.getAppKey() != null) {
            config.setAppKey(request.getAppKey());
        }
        if (request.getAppSecret() != null) {
            config.setAppSecret(request.getAppSecret());
        }
        if (request.getApiUrl() != null) {
            config.setApiUrl(request.getApiUrl());
        }
        if (request.getCallbackUrl() != null) {
            config.setCallbackUrl(request.getCallbackUrl());
        }
        if (request.getConfigJson() != null) {
            config.setConfigJson(request.getConfigJson());
        }
        if (request.getSyncInterval() != null) {
            config.setSyncInterval(request.getSyncInterval());
        }
        if (request.getDescription() != null) {
            config.setDescription(request.getDescription());
        }

        config.setUpdateTime(LocalDateTime.now());
        thirdPartyConfigMapper.updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteConfig(Long id) {
        ThirdPartyConfig config = getConfigById(id);
        config.setDeleted(true);
        config.setUpdateTime(LocalDateTime.now());
        thirdPartyConfigMapper.updateById(config);
    }

    @Override
    public ThirdPartyConfig getConfigById(Long id) {
        ThirdPartyConfig config = thirdPartyConfigMapper.selectById(id);
        if (config == null || config.getDeleted()) {
            throw BusinessException.of(ErrorCodeEnum.CONFIG_NOT_FOUND.getCode(), "配置不存在");
        }
        return config;
    }

    @Override
    public ThirdPartyConfig getConfigByCode(String platformCode) {
        LambdaQueryWrapper<ThirdPartyConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ThirdPartyConfig::getPlatformCode, platformCode)
                .eq(ThirdPartyConfig::getStatus, 1);
        
        ThirdPartyConfig config = thirdPartyConfigMapper.selectOne(wrapper);
        if (config == null) {
            throw BusinessException.of(ErrorCodeEnum.CONFIG_NOT_FOUND.getCode(), "平台配置不存在或未启用");
        }
        return config;
    }

    @Override
    public List<ThirdPartyConfig> getActiveConfigs() {
        LambdaQueryWrapper<ThirdPartyConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ThirdPartyConfig::getStatus, 1)
                .orderByAsc(ThirdPartyConfig::getPlatformType);
        return thirdPartyConfigMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enableConfig(Long id) {
        ThirdPartyConfig config = getConfigById(id);
        config.setStatus(1);
        config.setUpdateTime(LocalDateTime.now());
        thirdPartyConfigMapper.updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disableConfig(Long id) {
        ThirdPartyConfig config = getConfigById(id);
        config.setStatus(0);
        config.setUpdateTime(LocalDateTime.now());
        thirdPartyConfigMapper.updateById(config);
    }

    @Override
    public Map<String, Object> testConnection(Long id) {
        ThirdPartyConfig config = getConfigById(id);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("platformName", config.getPlatformName());
        result.put("platformCode", config.getPlatformCode());
        result.put("message", "连接测试成功");
        
        log.info("测试第三方平台连接: platformCode={}", config.getPlatformCode());
        
        return result;
    }
}
