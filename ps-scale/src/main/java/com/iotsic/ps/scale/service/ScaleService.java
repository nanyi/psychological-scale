package com.iotsic.ps.scale.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.Scale;
import com.iotsic.ps.scale.dto.ScaleCreateRequest;
import com.iotsic.ps.scale.dto.ScaleUpdateRequest;

/**
 * 量表服务接口
 * 负责量表的创建、查询、更新、发布、删除等业务操作
 *
 * @author Ryan
 * @since 2026-03-12
 */
public interface ScaleService {

    /**
     * 根据ID获取量表
     *
     * @param id 量表ID
     * @return 量表实体
     */
    Scale getScaleById(Long id);

    /**
     * 根据编码获取量表
     *
     * @param code 量表编码
     * @return 量表实体
     */
    Scale getScaleByCode(String code);

    /**
     * 创建量表
     *
     * @param request 量表创建请求
     * @return 创建的量表实体
     */
    Scale createScale(ScaleCreateRequest request);

    /**
     * 更新量表
     *
     * @param id 量表ID
     * @param request 量表更新请求
     */
    void updateScale(Long id, ScaleUpdateRequest request);

    /**
     * 发布量表
     *
     * @param id 量表ID
     */
    void publishScale(Long id);

    /**
     * 下线量表
     *
     * @param id 量表ID
     */
    void offlineScale(Long id);

    /**
     * 删除量表
     *
     * @param id 量表ID
     */
    void deleteScale(Long id);

    /**
     * 获取量表列表
     *
     * @param request 分页请求
     * @param categoryId 分类ID
     * @param status 量表状态
     * @return 量表分页结果
     */
    PageResult<Scale> getScaleList(PageRequest request, Long categoryId, Integer status);

    /**
     * 增加量表浏览次数
     *
     * @param id 量表ID
     */
    void incrementViewCount(Long id);

    /**
     * 增加量表使用次数
     *
     * @param id 量表ID
     */
    void incrementUseCount(Long id);
}
