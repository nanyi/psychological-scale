package com.iotsic.ps.scale.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iotsic.ps.core.entity.ScaleCategory;
import com.iotsic.ps.scale.mapper.ScaleCategoryMapper;
import com.iotsic.ps.scale.service.ScaleCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 量表分类服务实现
 * 
 * @author Ryan
 * @since 2026-03-17
 */
@Service
public class ScaleCategoryServiceImpl extends ServiceImpl<ScaleCategoryMapper, ScaleCategory> 
    implements ScaleCategoryService {

    @Override
    public List<ScaleCategory> getCategoryTree() {
        List<ScaleCategory> allCategories = list(
            new LambdaQueryWrapper<ScaleCategory>()
                .eq(ScaleCategory::getStatus, 1)
                .orderByAsc(ScaleCategory::getSortOrder)
        );
        
        if (CollectionUtils.isEmpty(allCategories)) {
            return new ArrayList<>();
        }
        
        // 获取一级分类
        List<ScaleCategory> rootCategories = allCategories.stream()
            .filter(c -> c.getParentId() == null || c.getParentId() == 0L)
            .collect(Collectors.toList());
        
        // 递归构建树形结构
        for (ScaleCategory root : rootCategories) {
            buildTree(root, allCategories);
        }
        
        return rootCategories;
    }

    private void buildTree(ScaleCategory parent, List<ScaleCategory> allCategories) {
        List<ScaleCategory> children = allCategories.stream()
            .filter(c -> parent.getId().equals(c.getParentId()))
            .collect(Collectors.toList());
        
        parent.setChildren(children);
        for (ScaleCategory child : children) {
            buildTree(child, allCategories);
        }
    }

    @Override
    public List<ScaleCategory> getAllEnabled() {
        return list(
            new LambdaQueryWrapper<ScaleCategory>()
                .eq(ScaleCategory::getStatus, 1)
                .orderByAsc(ScaleCategory::getSortOrder)
        );
    }
}
