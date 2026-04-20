package com.iotsic.ps.scale.thirdparty.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.scale.thirdparty.entity.ThirdPartyConfig;
import com.iotsic.ps.scale.thirdparty.mapper.ThirdPartyConfigMapper;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThirdPartyConfigServiceImpl implements ThirdPartyConfigService {

    private final ThirdPartyConfigMapper thirdPartyConfigMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ThirdPartyConfig createConfig(ThirdPartyConfig config) {
        LambdaQueryWrapper<ThirdPartyConfig> existWrapper = new LambdaQueryWrapper<>();
        existWrapper.eq(ThirdPartyConfig::getPlatformCode, config.getPlatformCode());
        if (thirdPartyConfigMapper.selectOne(existWrapper) != null) {
            throw new RuntimeException("平台代码已存在");
        }
        
        config.setStatus(1);
        config.setCreateTime(LocalDateTime.now());
        config.setUpdateTime(LocalDateTime.now());
        
        thirdPartyConfigMapper.insert(config);
        log.info("创建第三方平台配置: platformCode={}", config.getPlatformCode());
        
        return config;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ThirdPartyConfig updateConfig(ThirdPartyConfig config) {
        config.setUpdateTime(LocalDateTime.now());
        thirdPartyConfigMapper.updateById(config);
        return config;
    }

    @Override
    public PageResult<ThirdPartyConfig> getConfigList(PageRequest request) {
        Page<ThirdPartyConfig> page = new Page<>(request.getCurrent(), request.getSize());
        IPage<ThirdPartyConfig> result = thirdPartyConfigMapper.selectPage(page, null);
        return PageResult.of(result.getRecords(), result.getTotal());
    }

    @Override
    public ThirdPartyConfig getConfigById(Long configId) {
        return thirdPartyConfigMapper.selectById(configId);
    }

    @Override
    public ThirdPartyConfig getConfigByCode(String platformCode) {
        LambdaQueryWrapper<ThirdPartyConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ThirdPartyConfig::getPlatformCode, platformCode)
                .eq(ThirdPartyConfig::getStatus, 1);
        
        ThirdPartyConfig config = thirdPartyConfigMapper.selectOne(wrapper);
        if (config == null) {
            throw new RuntimeException("平台配置不存在或未启用");
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
    public void updateConfigStatus(Long configId, Integer status) {
        ThirdPartyConfig config = new ThirdPartyConfig();
        config.setId(configId);
        config.setStatus(status);
        config.setUpdateTime(LocalDateTime.now());
        thirdPartyConfigMapper.updateById(config);
    }
}