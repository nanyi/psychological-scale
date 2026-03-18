package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.core.entity.ScaleCategory;
import com.iotsic.ps.scale.dto.ScaleCategoryRequest;
import com.iotsic.ps.scale.dto.ScaleCategoryUpdateRequest;
import com.iotsic.ps.scale.mapper.ScaleCategoryMapper;
import com.iotsic.ps.scale.service.ScaleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@RequiredArgsConstructor
public class ScaleCategoryServiceImpl implements ScaleCategoryService {

    private final ScaleCategoryMapper scaleCategoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCategory(ScaleCategoryRequest request) {
        ScaleCategory category = new ScaleCategory();
        category.setCategoryName(request.getCategoryName());
        category.setParentId(request.getParentId() == null ? 0L : request.getParentId());
        category.setSortOrder(request.getSortOrder() == null ? 0 : request.getSortOrder());
        category.setRemark(request.getRemark());
        category.setStatus(request.getStatus() == null ? 1 : request.getStatus());
        scaleCategoryMapper.insert(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(ScaleCategoryUpdateRequest request) {
        ScaleCategory category = scaleCategoryMapper.selectById(request.getId());
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        category.setCategoryName(request.getCategoryName());
        if (request.getParentId() != null) {
            category.setParentId(request.getParentId());
        }
        if (request.getSortOrder() != null) {
            category.setSortOrder(request.getSortOrder());
        }
        category.setRemark(request.getRemark());
        if (request.getStatus() != null) {
            category.setStatus(request.getStatus());
        }
        scaleCategoryMapper.updateById(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        // 检查是否有子分类
        long childCount = scaleCategoryMapper.selectCount(
                new LambdaQueryWrapper<ScaleCategory>()
                        .eq(ScaleCategory::getParentId, id)
        );
        if (childCount > 0) {
            throw new BusinessException("该分类下有子分类，无法删除");
        }
        scaleCategoryMapper.deleteById(id);
    }

    @Override
    public List<ScaleCategory> getCategoryTree() {
        List<ScaleCategory> allCategories = scaleCategoryMapper.selectList(
            new LambdaQueryWrapper<ScaleCategory>()
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
        return scaleCategoryMapper.selectList(
            new LambdaQueryWrapper<ScaleCategory>()
                .eq(ScaleCategory::getStatus, 1)
                .orderByAsc(ScaleCategory::getSortOrder)
        );
    }
}
