package com.iotsic.ps.api.scale;

import com.iotsic.ps.api.config.FeignConfig;
import com.iotsic.ps.common.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "ps-scale", contextId = "scaleApi", configuration = FeignConfig.class)
public interface ScaleApi {

    @GetMapping("/api/scale/{id}")
    RestResult<Map<String, Object>> getScaleById(@PathVariable("id") Long id);

    @GetMapping("/api/scale/code/{code}")
    RestResult<Map<String, Object>> getScaleByCode(@PathVariable("code") String code);

    @GetMapping("/api/scale/list")
    RestResult<Map<String, Object>> getScaleList(@RequestParam Map<String, Object> params);

    @GetMapping("/api/scale/dimensions/{scaleId}")
    RestResult<Map<String, Object>> getDimensionsByScaleId(@PathVariable("scaleId") Long scaleId);

    @GetMapping("/api/scale/questions/{scaleId}")
    RestResult<Map<String, Object>> getQuestionsByScaleId(@PathVariable("scaleId") Long scaleId);

    @PostMapping("/api/scale/purchase/check")
    RestResult<Map<String, Object>> checkPurchase(@RequestParam("userId") Long userId,
                                                   @RequestParam("scaleId") Long scaleId);

    @PostMapping("/api/scale/use/record")
    RestResult<Void> recordScaleUse(@RequestParam("scaleId") Long scaleId);
}
