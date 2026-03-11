package com.iotsic.ps.thirdparty.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.utils.JsonUtils;
import com.iotsic.ps.thirdparty.entity.ThirdPartyCallback;
import com.iotsic.ps.thirdparty.entity.ThirdPartyConfig;
import com.iotsic.ps.thirdparty.mapper.CallbackMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackServiceImpl implements CallbackService {

    private final CallbackMapper callbackMapper;
    private final ThirdPartyConfigService thirdPartyConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> handleReportCallback(Long configId, Map<String, Object> params) {
        ThirdPartyConfig config = thirdPartyConfigService.getConfigById(configId);
        
        String callbackType = (String) params.getOrDefault("callbackType", "REPORT_GENERATED");
        String externalRecordId = (String) params.get("externalRecordId");
        
        ThirdPartyCallback callback = new ThirdPartyCallback();
        callback.setConfigId(configId);
        callback.setPlatformCode(config.getPlatformCode());
        callback.setCallbackType(callbackType);
        callback.setExternalRecordId(externalRecordId);
        callback.setRequestData(JsonUtils.toJson(params));
        callback.setCallbackStatus(0);
        callback.setCreateTime(LocalDateTime.now());
        callback.setUpdateTime(LocalDateTime.now());
        
        callbackMapper.insert(callback);

        try {
            processReportCallback(config, params);
            
            callback.setCallbackStatus(1);
            callback.setResponseData("{\"status\": \"success\"}");
            callback.setProcessTime(LocalDateTime.now());
            
            log.info("报告回调处理成功: platformCode={}, externalRecordId={}", 
                    config.getPlatformCode(), externalRecordId);
            
        } catch (Exception e) {
            callback.setCallbackStatus(2);
            callback.setErrorMessage(e.getMessage());
            
            log.error("报告回调处理失败: platformCode={}, externalRecordId={}", 
                    config.getPlatformCode(), externalRecordId, e);
        }
        
        callbackMapper.updateById(callback);

        Map<String, Object> result = new HashMap<>();
        result.put("callbackId", callback.getId());
        result.put("status", callback.getCallbackStatus());
        
        return result;
    }

    @Override
    public ThirdPartyCallback getCallbackById(Long id) {
        ThirdPartyCallback callback = callbackMapper.selectById(id);
        if (callback == null || callback.getDeleted() == 1) {
            throw BusinessException.of("CALLBACK_NOT_FOUND", "回调记录不存在");
        }
        return callback;
    }

    @Override
    public List<ThirdPartyCallback> getCallbacksByRecordId(String externalRecordId) {
        LambdaQueryWrapper<ThirdPartyCallback> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ThirdPartyCallback::getExternalRecordId, externalRecordId)
                .orderByDesc(ThirdPartyCallback::getCreateTime);
        return callbackMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void retryCallback(Long callbackId) {
        ThirdPartyCallback callback = getCallbackById(callbackId);
        
        callback.setCallbackStatus(0);
        callback.setUpdateTime(LocalDateTime.now());
        callbackMapper.updateById(callback);
        
        try {
            Map<String, Object> params = JsonUtils.fromJson(
                    callback.getRequestData(), 
                    new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
            
            ThirdPartyConfig config = thirdPartyConfigService.getConfigById(callback.getConfigId());
            processReportCallback(config, params);
            
            callback.setCallbackStatus(1);
            callback.setResponseData("{\"status\": \"success\"}");
            callback.setProcessTime(LocalDateTime.now());
            
            log.info("回调重试成功: callbackId={}", callbackId);
            
        } catch (Exception e) {
            callback.setCallbackStatus(2);
            callback.setErrorMessage(e.getMessage());
            
            log.error("回调重试失败: callbackId={}", callbackId, e);
        }
        
        callbackMapper.updateById(callback);
    }

    private void processReportCallback(ThirdPartyConfig config, Map<String, Object> params) {
        String externalRecordId = (String) params.get("externalRecordId");
        String reportStatus = (String) params.get("reportStatus");
        
        log.info("处理报告回调: platformCode={}, externalRecordId={}, status={}", 
                config.getPlatformCode(), externalRecordId, reportStatus);
    }
}
