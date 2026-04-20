package com.iotsic.ps.scale.controller;

import com.iotsic.ps.core.entity.ScaleCategory;
import com.iotsic.ps.scale.dto.ScaleCategoryRequest;
import com.iotsic.ps.scale.dto.ScaleCategoryUpdateRequest;
import com.iotsic.ps.scale.service.ScaleCategoryService;
import com.iotsic.smart.framework.common.result.RestResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        scaleCategoryService.createCategory(request);
        return RestResult.success();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "更新分类")
    public RestResult<Void> updateCategory(@PathVariable Long id, @RequestBody ScaleCategoryUpdateRequest request) {
        if (request.getId() == null) {
            request.setId(id);
        }
        scaleCategoryService.updateCategory(request);
        return RestResult.success();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除分类")
    public RestResult<Void> deleteCategory(@PathVariable Long id) {
        scaleCategoryService.deleteCategory(id);
        return RestResult.success();
    }

    /**
     * 启用分类
     *
     * @param id 分类ID
     * @return 操作结果
     */
    @PutMapping("/enable/{id}")
    public RestResult<Void> enableCategory(@PathVariable Long id) {
        scaleCategoryService.enableCategory(id);
        return RestResult.success();
    }

    /**
     * 禁用分类
     *
     * @param id 分类ID
     * @return 操作结果
     */
    @PutMapping("/disable/{id}")
    public RestResult<Void> disableCategory(@PathVariable Long id) {
        scaleCategoryService.disableCategory(id);
        return RestResult.success();
    }
}
