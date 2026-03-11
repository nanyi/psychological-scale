package com.iotsic.ps.thirdparty.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.thirdparty.entity.SyncLog;
import com.iotsic.ps.thirdparty.entity.ThirdPartyConfig;
import com.iotsic.ps.thirdparty.mapper.SyncLogMapper;
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
public class ScaleSyncServiceImpl implements ScaleSyncService {

    private final SyncLogMapper syncLogMapper;
    private final ThirdPartyConfigService thirdPartyConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> syncScalesFromPlatform(Long configId) {
        ThirdPartyConfig config = thirdPartyConfigService.getConfigById(configId);
        
        SyncLog syncLog = new SyncLog();
        syncLog.setConfigId(configId);
        syncLog.setPlatformCode(config.getPlatformCode());
        syncLog.setSyncType(1);
        syncLog.setSyncStatus(0);
        syncLog.setStartTime(LocalDateTime.now());
        syncLog.setCreateTime(LocalDateTime.now());
        syncLog.setUpdateTime(LocalDateTime.now());
        
        syncLogMapper.insert(syncLog);

        try {
            List<Map<String, Object>> scaleList = fetchScalesFromPlatform(config);
            
            int syncCount = 0;
            for (Map<String, Object> scaleData : scaleList) {
                syncScaleToLocal(config, scaleData);
                syncCount++;
            }

            syncLog.setSyncStatus(1);
            syncLog.setSyncCount(syncCount);
            syncLog.setEndTime(LocalDateTime.now());
            syncLog.setResponseData("{\"total\": " + scaleList.size() + ", \"synced\": " + syncCount + "}");
            
            log.info("量表同步成功: platformCode={}, count={}", config.getPlatformCode(), syncCount);
            
        } catch (Exception e) {
            syncLog.setSyncStatus(2);
            syncLog.setErrorMessage(e.getMessage());
            syncLog.setEndTime(LocalDateTime.now());
            
            log.error("量表同步失败: platformCode={}", config.getPlatformCode(), e);
        }
        
        syncLogMapper.updateById(syncLog);

        Map<String, Object> result = new HashMap<>();
        result.put("syncLogId", syncLog.getId());
        result.put("syncStatus", syncLog.getSyncStatus());
        result.put("syncCount", syncLog.getSyncCount());
        result.put("startTime", syncLog.getStartTime());
        result.put("endTime", syncLog.getEndTime());
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> syncSingleScale(Long configId, String externalScaleId) {
        ThirdPartyConfig config = thirdPartyConfigService.getConfigById(configId);
        
        SyncLog syncLog = new SyncLog();
        syncLog.setConfigId(configId);
        syncLog.setPlatformCode(config.getPlatformCode());
        syncLog.setSyncType(2);
        syncLog.setSyncStatus(0);
        syncLog.setStartTime(LocalDateTime.now());
        syncLog.setCreateTime(LocalDateTime.now());
        syncLog.setUpdateTime(LocalDateTime.now());
        
        syncLogMapper.insert(syncLog);

        try {
            Map<String, Object> scaleData = fetchSingleScaleFromPlatform(config, externalScaleId);
            syncScaleToLocal(config, scaleData);

            syncLog.setSyncStatus(1);
            syncLog.setSyncCount(1);
            syncLog.setEndTime(LocalDateTime.now());
            syncLog.setResponseData("{\"externalScaleId\": \"" + externalScaleId + "\"}");
            
            log.info("单个量表同步成功: platformCode={}, externalScaleId={}", 
                    config.getPlatformCode(), externalScaleId);
            
        } catch (Exception e) {
            syncLog.setSyncStatus(2);
            syncLog.setErrorMessage(e.getMessage());
            syncLog.setEndTime(LocalDateTime.now());
            
            log.error("单个量表同步失败: platformCode={}, externalScaleId={}", 
                    config.getPlatformCode(), externalScaleId, e);
        }
        
        syncLogMapper.updateById(syncLog);

        Map<String, Object> result = new HashMap<>();
        result.put("syncLogId", syncLog.getId());
        result.put("syncStatus", syncLog.getSyncStatus());
        
        return result;
    }

    @Override
    public List<SyncLog> getSyncLogs(Long configId, Integer syncType) {
        LambdaQueryWrapper<SyncLog> wrapper = new LambdaQueryWrapper<>();
        
        if (configId != null) {
            wrapper.eq(SyncLog::getConfigId, configId);
        }
        if (syncType != null) {
            wrapper.eq(SyncLog::getSyncType, syncType);
        }
        
        wrapper.orderByDesc(SyncLog::getCreateTime);
        wrapper.last("LIMIT 50");
        
        return syncLogMapper.selectList(wrapper);
    }

    @Override
    public Map<String, Object> getSyncStatistics(Long configId) {
        LambdaQueryWrapper<SyncLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncLog::getConfigId, configId);
        
        List<SyncLog> logs = syncLogMapper.selectList(wrapper);
        
        int totalSyncs = logs.size();
        int successSyncs = (int) logs.stream()
                .filter(log -> log.getSyncStatus() != null && log.getSyncStatus() == 1)
                .count();
        int failedSyncs = (int) logs.stream()
                .filter(log -> log.getSyncStatus() != null && log.getSyncStatus() == 2)
                .count();
        
        long totalCount = logs.stream()
                .filter(log -> log.getSyncCount() != null)
                .mapToLong(SyncLog::getSyncCount)
                .sum();
        
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalSyncs", totalSyncs);
        statistics.put("successSyncs", successSyncs);
        statistics.put("failedSyncs", failedSyncs);
        statistics.put("totalCount", totalCount);
        
        return statistics;
    }

    private List<Map<String, Object>> fetchScalesFromPlatform(ThirdPartyConfig config) {
        log.info("从第三方平台获取量表列表: platformCode={}, apiUrl={}", 
                config.getPlatformCode(), config.getApiUrl());
        
        return List.of();
    }

    private Map<String, Object> fetchSingleScaleFromPlatform(ThirdPartyConfig config, String externalScaleId) {
        log.info("从第三方平台获取单个量表: platformCode={}, externalScaleId={}", 
                config.getPlatformCode(), externalScaleId);
        
        Map<String, Object> scaleData = new HashMap<>();
        scaleData.put("externalScaleId", externalScaleId);
        scaleData.put("scaleName", "同步量表-" + externalScaleId);
        
        return scaleData;
    }

    private void syncScaleToLocal(ThirdPartyConfig config, Map<String, Object> scaleData) {
        log.info("同步量表到本地: platformCode={}, data={}", 
                config.getPlatformCode(), scaleData);
    }
}
