package com.iotsic.ps.scale.thirdparty.service;

import com.iotsic.ps.scale.thirdparty.entity.ThirdPartyConfig;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;

import java.util.List;

public interface ThirdPartyConfigService {

    ThirdPartyConfig createConfig(ThirdPartyConfig config);

    ThirdPartyConfig updateConfig(ThirdPartyConfig config);

    PageResult<ThirdPartyConfig> getConfigList(PageRequest request);

    ThirdPartyConfig getConfigById(Long configId);

    ThirdPartyConfig getConfigByCode(String platformCode);

    List<ThirdPartyConfig> getActiveConfigs();

    void updateConfigStatus(Long configId, Integer status);
}