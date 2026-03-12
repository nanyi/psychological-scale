package com.iotsic.ps.scale.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.Scale;
import com.iotsic.ps.scale.dto.ScaleCreateRequest;
import com.iotsic.ps.scale.dto.ScaleUpdateRequest;

/**
 * 量表服务接口
 * 
 * @author Ryan
 * @since 2026-03-12
 */
public interface ScaleService {

    Scale getScaleById(Long id);

    Scale getScaleByCode(String code);

    Scale createScale(ScaleCreateRequest request);

    void updateScale(Long id, ScaleUpdateRequest request);

    void publishScale(Long id);

    void offlineScale(Long id);

    void deleteScale(Long id);

    PageResult<Scale> getScaleList(PageRequest request, Integer category, Integer status);

    void incrementViewCount(Long id);

    void incrementUseCount(Long id);
}
