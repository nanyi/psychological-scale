package com.iotsic.ps.thirdparty.service;

import com.iotsic.ps.thirdparty.entity.ThirdPartyConfig;

import java.util.List;
import java.util.Map;

public interface ThirdPartyConfigService {

    ThirdPartyConfig createConfig(Map<String, Object> params);

    void updateConfig(Long id, Map<String, Object> params);

    void deleteConfig(Long id);

    ThirdPartyConfig getConfigById(Long id);

    ThirdPartyConfig getConfigByCode(String platformCode);

    List<ThirdPartyConfig> getActiveConfigs();

    void enableConfig(Long id);

    void disableConfig(Long id);

    Map<String, Object> testConnection(Long id);
}
