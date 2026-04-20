package com.iotsic.ps.scale.thirdparty.controller;

import com.iotsic.ps.scale.thirdparty.service.CallbackService;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 第三方回调控制器
 * 负责处理第三方平台的回调请求
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@RestController
@RequestMapping("/api/callback")
@RequiredArgsConstructor
public class ThirdPartyCallbackController {

    private final CallbackService callbackService;

    @PostMapping("/{configId}")
    public RestResult<?> handleCallback(
            @PathVariable Long configId,
            @RequestParam String callbackType,
            @RequestBody String data) {
        callbackService.processCallback(configId, callbackType, data);
        return RestResult.success();
    }
}