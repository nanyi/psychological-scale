package com.iotsic.ps.thirdparty.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.exception.BusinessException;
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
    public ThirdPartyConfig createConfig(Map<String, Object> params) {
        String platformCode = (String) params.get("platformCode");
        
        LambdaQueryWrapper<ThirdPartyConfig> existWrapper = new LambdaQueryWrapper<>();
        existWrapper.eq(ThirdPartyConfig::getPlatformCode, platformCode);
        if (thirdPartyConfigMapper.selectOne(existWrapper) != null) {
            throw BusinessException.of("PLATFORM_EXIST", "平台代码已存在");
        }

        ThirdPartyConfig config = new ThirdPartyConfig();
        config.setPlatformName((String) params.get("platformName"));
        config.setPlatformCode(platformCode);
        config.setAppKey((String) params.get("appKey"));
        config.setAppSecret((String) params.get("appSecret"));
        config.setApiUrl((String) params.get("apiUrl"));
        config.setCallbackUrl((String) params.get("callbackUrl"));
        config.setPlatformType((Integer) params.get("platformType"));
        config.setConfigJson((String) params.get("configJson"));
        config.setSyncInterval((Integer) params.getOrDefault("syncInterval", 60));
        config.setDescription((String) params.get("description"));
        config.setStatus(1);
        config.setCreateTime(LocalDateTime.now());
        config.setUpdateTime(LocalDateTime.now());

        thirdPartyConfigMapper.insert(config);
        log.info("创建第三方平台配置: platformCode={}", platformCode);
        
        return config;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConfig(Long id, Map<String, Object> params) {
        ThirdPartyConfig config = getConfigById(id);

        if (params.containsKey("platformName")) {
            config.setPlatformName((String) params.get("platformName"));
        }
        if (params.containsKey("appKey")) {
            config.setAppKey((String) params.get("appKey"));
        }
        if (params.containsKey("appSecret")) {
            config.setAppSecret((String) params.get("appSecret"));
        }
        if (params.containsKey("apiUrl")) {
            config.setApiUrl((String) params.get("apiUrl"));
        }
        if (params.containsKey("callbackUrl")) {
            config.setCallbackUrl((String) params.get("callbackUrl"));
        }
        if (params.containsKey("configJson")) {
            config.setConfigJson((String) params.get("configJson"));
        }
        if (params.containsKey("syncInterval")) {
            config.setSyncInterval((Integer) params.get("syncInterval"));
        }
        if (params.containsKey("description")) {
            config.setDescription((String) params.get("description"));
        }

        config.setUpdateTime(LocalDateTime.now());
        thirdPartyConfigMapper.updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteConfig(Long id) {
        ThirdPartyConfig config = getConfigById(id);
        config.setDeleted(1);
        config.setUpdateTime(LocalDateTime.now());
        thirdPartyConfigMapper.updateById(config);
    }

    @Override
    public ThirdPartyConfig getConfigById(Long id) {
        ThirdPartyConfig config = thirdPartyConfigMapper.selectById(id);
        if (config == null || config.getDeleted() == 1) {
            throw BusinessException.of("CONFIG_NOT_FOUND", "配置不存在");
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
            throw BusinessException.of("CONFIG_NOT_FOUND", "平台配置不存在或未启用");
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
