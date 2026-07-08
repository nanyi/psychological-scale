package com.iotsic.smart.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.smart.framework.common.exception.BusinessException;
import com.iotsic.smart.framework.common.utils.json.JsonUtils;
import com.iotsic.smart.framework.cache.utils.RedisUtils;
import com.iotsic.smart.system.dto.SysConfigRequest;
import com.iotsic.smart.system.dto.SysConfigVO;
import com.iotsic.smart.system.entity.SysConfig;
import com.iotsic.smart.system.mapper.SysConfigMapper;
import com.iotsic.smart.system.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统配置服务实现
 *
 * @author Ryan
 * @since 2026-07-08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl implements SysConfigService {

    private static final String CACHE_KEY = "system:config:all";

    private final SysConfigMapper sysConfigMapper;

    @Override
    public Map<String, Map<String, SysConfigVO>> getAllConfig() {
        String cached = RedisUtils.get(CACHE_KEY);
        if (cached != null) {
            return JsonUtils.parseObject(cached, new TypeReference<Map<String, Map<String, SysConfigVO>>>() {});
        }

        Map<String, Map<String, SysConfigVO>> result = loadAndCacheConfig();
        return result;
    }

    @Override
    public List<SysConfigVO> getConfigByCategory(String category) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getCategory, category)
               .eq(SysConfig::getDeleted, 0)
               .eq(SysConfig::getVisible, 1);
        List<SysConfig> list = sysConfigMapper.selectList(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateConfig(Long id, String configValue) {
        SysConfig config = sysConfigMapper.selectById(id);
        if (config == null) {
            throw BusinessException.of(1400, "配置不存在");
        }
        config.setConfigValue(configValue);
        sysConfigMapper.updateById(config);

        RedisUtils.delete(CACHE_KEY);
        log.info("配置已更新: id={}, key={}", id, config.getConfigKey());
    }

    @Override
    @Transactional
    public void addConfig(SysConfigRequest request) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getCategory, request.getCategory())
               .eq(SysConfig::getConfigKey, request.getConfigKey())
               .eq(SysConfig::getDeleted, 0);
        if (sysConfigMapper.selectCount(wrapper) > 0) {
            throw BusinessException.of(1402, "配置键已存在");
        }

        SysConfig config = new SysConfig();
        config.setCategory(request.getCategory());
        config.setConfigType(request.getConfigType());
        config.setConfigName(request.getConfigName());
        config.setConfigKey(request.getConfigKey());
        config.setConfigValue(request.getConfigValue());
        config.setVisible(request.getVisible() != null ? request.getVisible() : true);
        config.setIsSystem(false);
        config.setRemark(request.getRemark());
        sysConfigMapper.insert(config);

        RedisUtils.delete(CACHE_KEY);
        log.info("配置已新增: key={}", request.getConfigKey());
    }

    @Override
    @Transactional
    public void deleteConfig(Long id) {
        SysConfig config = sysConfigMapper.selectById(id);
        if (config == null) {
            throw BusinessException.of(1400, "配置不存在");
        }
        if (Boolean.TRUE.equals(config.getIsSystem())) {
            throw BusinessException.of(1403, "系统内置配置不可删除");
        }
        sysConfigMapper.deleteById(id);

        RedisUtils.delete(CACHE_KEY);
        log.info("配置已删除: id={}, key={}", id, config.getConfigKey());
    }

    @Override
    public void refreshCache() {
        loadAndCacheConfig();
        log.info("配置缓存已刷新");
    }

    private Map<String, Map<String, SysConfigVO>> loadAndCacheConfig() {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getDeleted, 0);
        List<SysConfig> list = sysConfigMapper.selectList(wrapper);

        Map<String, Map<String, SysConfigVO>> result = new HashMap<>();
        for (SysConfig config : list) {
            String category = config.getCategory();
            result.computeIfAbsent(category, k -> new HashMap<>())
                  .put(config.getConfigKey(), convertToVO(config));
        }

        RedisUtils.set(CACHE_KEY, JsonUtils.toJSONString(result));
        return result;
    }

    private SysConfigVO convertToVO(SysConfig config) {
        SysConfigVO vo = new SysConfigVO();
        vo.setId(config.getId());
        vo.setCategory(config.getCategory());
        vo.setConfigType(config.getConfigType());
        vo.setConfigName(config.getConfigName());
        vo.setConfigKey(config.getConfigKey());
        vo.setConfigValue(config.getConfigValue());
        vo.setVisible(config.getVisible());
        vo.setIsSystem(config.getIsSystem());
        vo.setRemark(config.getRemark());
        return vo;
    }
}
