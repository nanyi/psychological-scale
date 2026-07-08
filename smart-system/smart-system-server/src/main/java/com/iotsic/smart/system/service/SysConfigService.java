package com.iotsic.smart.system.service;

import com.iotsic.smart.system.dto.SysConfigRequest;
import com.iotsic.smart.system.dto.SysConfigVO;

import java.util.List;
import java.util.Map;

/**
 * 系统配置服务接口
 *
 * @author Ryan
 * @since 2026-07-08
 */
public interface SysConfigService {

    /**
     * 获取所有配置（按分组）
     *
     * @return 配置 Map
     */
    Map<String, Map<String, SysConfigVO>> getAllConfig();

    /**
     * 获取指定分组的配置
     *
     * @param category 分组
     * @return 配置列表
     */
    List<SysConfigVO> getConfigByCategory(String category);

    /**
     * 更新配置
     *
     * @param id 配置ID
     * @param configValue 新值
     */
    void updateConfig(Long id, String configValue);

    /**
     * 新增配置
     *
     * @param request 配置请求
     */
    void addConfig(SysConfigRequest request);

    /**
     * 删除配置
     *
     * @param id 配置ID
     */
    void deleteConfig(Long id);

    /**
     * 刷新配置缓存
     */
    void refreshCache();
}
