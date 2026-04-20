package com.iotsic.ps.api.scale;

import com.iotsic.ps.api.config.FeignConfig;
import com.iotsic.ps.api.dto.DimensionListResponse;
import com.iotsic.ps.api.dto.PurchaseCheckResponse;
import com.iotsic.ps.api.dto.QuestionListResponse;
import com.iotsic.ps.api.dto.ScaleListResponse;
import com.iotsic.ps.api.dto.ScaleResponse;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import com.iotsic.smart.framework.common.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ps-scale", contextId = "scaleApi", configuration = FeignConfig.class)
public interface ScaleApi {

    @GetMapping("/api/scale/{id}")
    RestResult<ScaleResponse> getScaleById(@PathVariable("id") Long id);

    @GetMapping("/api/scale/by-code/{code}")
    RestResult<ScaleResponse> getScaleByCode(@PathVariable("code") String code);

    @GetMapping("/api/scale/list")
    RestResult<PageResult<ScaleListResponse>> getScaleList(PageRequest request);

    @GetMapping("/api/scale/dimensions/{scaleId}")
    RestResult<DimensionListResponse> getDimensionsByScaleId(@PathVariable("scaleId") Long scaleId);

    @GetMapping("/api/scale/questions/{scaleId}")
    RestResult<QuestionListResponse> getQuestionsByScaleId(@PathVariable("scaleId") Long scaleId);

    @PostMapping("/api/scale/purchase/check")
    RestResult<PurchaseCheckResponse> checkPurchase(@RequestParam("userId") Long userId,
                                                    @RequestParam("scaleId") Long scaleId);

    @PostMapping("/api/scale/use/record")
    RestResult<Void> recordScaleUse(@RequestParam("scaleId") Long scaleId);
}
