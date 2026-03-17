package com.iotsic.ps.scale.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.core.entity.ScaleCategory;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.scale.dto.ScaleCategoryRequest;
import com.iotsic.ps.scale.service.ScaleCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 量表分类控制器
 * 
 * @author Ryan
 * @since 2026-03-17
 */
@RestController
@RequestMapping("/api/scale-category")
@RequiredArgsConstructor
@Tag(name = "量表分类管理")
public class ScaleCategoryController {

    private final ScaleCategoryService scaleCategoryService;

    @GetMapping("/list")
    @Operation(summary = "获取分类树形列表")
    public RestResult<List<ScaleCategory>> getCategoryTree() {
        return RestResult.success(scaleCategoryService.getCategoryTree());
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有分类（下拉选择用）")
    public RestResult<List<ScaleCategory>> getAllCategories() {
        return RestResult.success(scaleCategoryService.getAllEnabled());
    }

    @PostMapping("/create")
    @Operation(summary = "新增分类")
    public RestResult<Void> createCategory(@RequestBody ScaleCategoryRequest request) {
        ScaleCategory category = new ScaleCategory();
        category.setCategoryName(request.getCategoryName());
        category.setParentId(request.getParentId() == null ? 0L : request.getParentId());
        category.setSortOrder(request.getSortOrder() == null ? 0 : request.getSortOrder());
        category.setRemark(request.getRemark());
        category.setStatus(request.getStatus() == null ? 1 : request.getStatus());
        scaleCategoryService.save(category);
        return RestResult.success();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "更新分类")
    public RestResult<Void> updateCategory(@PathVariable Long id, @RequestBody ScaleCategoryRequest request) {
        ScaleCategory category = scaleCategoryService.getById(id);
        if (category == null) {
            return RestResult.fail("分类不存在");
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
        scaleCategoryService.updateById(category);
        return RestResult.success();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除分类")
    public RestResult<Void> deleteCategory(@PathVariable Long id) {
        // 检查是否有子分类
        long childCount = scaleCategoryService.count(
            new LambdaQueryWrapper<ScaleCategory>()
                .eq(ScaleCategory::getParentId, id)
        );
        if (childCount > 0) {
            return RestResult.fail("该分类下有子分类，无法删除");
        }
        scaleCategoryService.removeById(id);
        return RestResult.success();
    }
}
