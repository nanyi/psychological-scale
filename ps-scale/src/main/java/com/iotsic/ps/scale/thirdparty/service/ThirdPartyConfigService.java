package com.iotsic.ps.scale.thirdparty.service;

import com.iotsic.ps.scale.thirdparty.dto.ThirdPartyConfigCreateRequest;
import com.iotsic.ps.scale.thirdparty.dto.ThirdPartyConfigUpdateRequest;
import com.iotsic.ps.scale.thirdparty.entity.ThirdPartyConfig;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;

import java.util.List;
import java.util.Map;

public interface ThirdPartyConfigService {

    ThirdPartyConfig createConfig(ThirdPartyConfigCreateRequest request);

    ThirdPartyConfig updateConfig(Long id, ThirdPartyConfigUpdateRequest request);

    void deleteConfig(Long id);

    PageResult<ThirdPartyConfig> getConfigList(PageRequest request);

    ThirdPartyConfig getConfigById(Long id);

    ThirdPartyConfig getConfigByCode(String platformCode);

    List<ThirdPartyConfig> getActiveConfigs();

    void enableConfig(Long id);

    void disableConfig(Long id);

    void updateConfigStatus(Long configId, Integer status);

    Map<String, Object> testConnection(Long id);
}
