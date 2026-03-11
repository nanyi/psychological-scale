package com.iotsic.ps.scale.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.core.entity.Scale;
import com.iotsic.ps.scale.service.ScaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/scale")
@RequiredArgsConstructor
public class ScaleController {

    private final ScaleService scaleService;

    @GetMapping("/{id}")
    public RestResult<Scale> getScaleById(@PathVariable Long id) {
        scaleService.incrementViewCount(id);
        return RestResult.success(scaleService.getScaleById(id));
    }

    @GetMapping("/code/{code}")
    public RestResult<Scale> getScaleByCode(@PathVariable String code) {
        return RestResult.success(scaleService.getScaleByCode(code));
    }

    @PostMapping("/create")
    public RestResult<Scale> createScale(@RequestBody Map<String, Object> params) {
        Scale scale = scaleService.createScale(params);
        return RestResult.success("量表创建成功", scale);
    }

    @PutMapping("/{id}")
    public RestResult<Void> updateScale(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        scaleService.updateScale(id, params);
        return RestResult.success();
    }

    @PostMapping("/{id}/publish")
    public RestResult<Void> publishScale(@PathVariable Long id) {
        scaleService.publishScale(id);
        return RestResult.success();
    }

    @PostMapping("/{id}/offline")
    public RestResult<Void> offlineScale(@PathVariable Long id) {
        scaleService.offlineScale(id);
        return RestResult.success();
    }

    @DeleteMapping("/{id}")
    public RestResult<Void> deleteScale(@PathVariable Long id) {
        scaleService.deleteScale(id);
        return RestResult.success();
    }

    @GetMapping("/list")
    public RestResult<PageResult<Scale>> getScaleList(
            PageRequest request,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) Integer status) {
        return RestResult.success(scaleService.getScaleList(request, category, status));
    }

    @PostMapping("/{id}/use")
    public RestResult<Void> recordScaleUse(@PathVariable Long id) {
        scaleService.incrementUseCount(id);
        return RestResult.success();
    }
}
