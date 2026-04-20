package com.iotsic.ps.scale.controller;

import com.iotsic.ps.api.dto.ScaleResponse;
import com.iotsic.ps.core.entity.Scale;
import com.iotsic.ps.scale.dto.ScaleCreateRequest;
import com.iotsic.ps.scale.dto.ScaleUpdateRequest;
import com.iotsic.ps.scale.service.ScaleService;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 量表控制器
 * 负责量表的创建、查询、更新、发布等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/scale")
@RequiredArgsConstructor
public class ScaleController {

    private final ScaleService scaleService;

    /**
     * 根据ID获取量表
     * 
     * @param id 量表ID
     * @return 量表信息
     */
    @GetMapping("/detail/{id}")
    public RestResult<Scale> getScaleById(@PathVariable Long id) {
        scaleService.incrementViewCount(id);
        return RestResult.success(scaleService.getScaleById(id));
    }

    /**
     * 根据编码获取量表
     * 
     * @param code 量表编码
     * @return 量表信息
     */
    @GetMapping("/by-code/{code}")
    public RestResult<Scale> getScaleByCode(@PathVariable String code) {
        return RestResult.success(scaleService.getScaleByCode(code));
    }

    /**
     * 创建量表
     * 
     * @param request 量表创建请求
     * @return 量表信息
     */
    @PostMapping("/create")
    public RestResult<Scale> createScale(@RequestBody ScaleCreateRequest request) {
        Scale scale = scaleService.createScale(request);
        return RestResult.success("量表创建成功", scale);
    }

    /**
     * 更新量表
     * 
     * @param id 量表ID
     * @param request 量表更新请求
     * @return 操作结果
     */
    @PutMapping("/update/{id}")
    public RestResult<Void> updateScale(@PathVariable Long id, @RequestBody ScaleUpdateRequest request) {
        scaleService.updateScale(id, request);
        return RestResult.success();
    }

    /**
     * 发布量表
     * 
     * @param id 量表ID
     * @return 操作结果
     */
    @PostMapping("/publish/{id}")
    public RestResult<Void> publishScale(@PathVariable Long id) {
        scaleService.publishScale(id);
        return RestResult.success();
    }

    /**
     * 下线量表
     * 
     * @param id 量表ID
     * @return 操作结果
     */
    @PostMapping("/offline/{id}")
    public RestResult<Void> offlineScale(@PathVariable Long id) {
        scaleService.offlineScale(id);
        return RestResult.success();
    }

    /**
     * 删除量表
     * 
     * @param id 量表ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public RestResult<Void> deleteScale(@PathVariable Long id) {
        scaleService.deleteScale(id);
        return RestResult.success();
    }

    /**
     * 获取量表列表
     * 
     * @param request 分页请求
     * @param categoryId 分类ID
     * @param status 状态
     * @return 量表分页列表
     */
    @GetMapping("/list")
    public RestResult<PageResult<ScaleResponse>> getScaleList(
            PageRequest request,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "status", required = false) Integer status) {
        return RestResult.success(scaleService.getScaleList(request, categoryId, status));
    }

    /**
     * 记录量表使用
     * 
     * @param id 量表ID
     * @return 操作结果
     */
    @PostMapping("/use/{id}")
    public RestResult<Void> recordScaleUse(@PathVariable Long id) {
        scaleService.incrementUseCount(id);
        return RestResult.success();
    }
}
