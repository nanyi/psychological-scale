package com.iotsic.ps.scale.service;

import com.iotsic.ps.core.entity.ScaleCategory;
import com.iotsic.ps.scale.dto.ScaleCategoryRequest;
import com.iotsic.ps.scale.dto.ScaleCategoryUpdateRequest;

import java.util.List;

/**
 * 量表分类服务接口
 * 
 * @author Ryan
 * @since 2026-03-17
 */
public interface ScaleCategoryService {

    /**
     * 新增分类
     *
     * @param request 分类信息
     */
    void createCategory(ScaleCategoryRequest request);

    /**
     * 更新分类
     *
     * @param category 分类信息
     */
    void updateCategory(ScaleCategoryUpdateRequest category);

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    void deleteCategory(Long id);

    /**
     * 启用分类
     *
     * @param id 分类ID
     */
    void enableCategory(Long id);

    /**
     * 禁用分类
     *
     * @param id 分类ID
     */
    void disableCategory(Long id);

    /**
     * 获取分类树形列表
     * 
     * @return 分类树
     */
    List<ScaleCategory> getCategoryTree();

    /**
     * 获取所有启用的分类（下拉选择用）
     * 
     * @return 分类列表
     */
    List<ScaleCategory> getAllEnabled();
}
