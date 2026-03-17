package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iotsic.ps.core.entity.ScaleCategory;

import java.util.List;

/**
 * 量表分类服务接口
 * 
 * @author Ryan
 * @since 2026-03-17
 */
public interface ScaleCategoryService extends IService<ScaleCategory> {

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
