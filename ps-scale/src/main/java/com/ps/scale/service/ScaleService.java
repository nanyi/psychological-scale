package com.ps.scale.service;

import com.ps.common.request.PageRequest;
import com.ps.common.response.PageResult;
import com.ps.core.entity.Scale;

import java.util.Map;

public interface ScaleService {

    Scale getScaleById(Long id);

    Scale getScaleByCode(String code);

    Scale createScale(Map<String, Object> params);

    void updateScale(Long id, Map<String, Object> params);

    void publishScale(Long id);

    void offlineScale(Long id);

    void deleteScale(Long id);

    PageResult<Scale> getScaleList(PageRequest request, Integer category, Integer status);

    void incrementViewCount(Long id);

    void incrementUseCount(Long id);
}
