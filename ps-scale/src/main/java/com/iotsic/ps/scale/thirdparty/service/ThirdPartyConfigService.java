package com.iotsic.ps.thirdparty.service;

import com.iotsic.ps.thirdparty.dto.ThirdPartyConfigCreateRequest;
import com.iotsic.ps.thirdparty.dto.ThirdPartyConfigUpdateRequest;
import com.iotsic.ps.thirdparty.entity.ThirdPartyConfig;

import java.util.List;
import java.util.Map;

public interface ThirdPartyConfigService {

    ThirdPartyConfig createConfig(ThirdPartyConfigCreateRequest request);

    void updateConfig(Long id, ThirdPartyConfigUpdateRequest request);

    void deleteConfig(Long id);

    ThirdPartyConfig getConfigById(Long id);

    ThirdPartyConfig getConfigByCode(String platformCode);

    List<ThirdPartyConfig> getActiveConfigs();

    void enableConfig(Long id);

    void disableConfig(Long id);

    Map<String, Object> testConnection(Long id);
}
