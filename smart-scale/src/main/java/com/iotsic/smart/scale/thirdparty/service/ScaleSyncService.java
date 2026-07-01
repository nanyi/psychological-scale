package com.iotsic.smart.scale.thirdparty.service;

import com.iotsic.smart.scale.thirdparty.dto.SyncResultResponse;
import com.iotsic.smart.scale.thirdparty.dto.SyncStatisticsResponse;
import com.iotsic.smart.scale.thirdparty.entity.SyncLog;

import java.util.List;

public interface ScaleSyncService {

    SyncResultResponse syncScalesFromPlatform(Long configId);

    SyncResultResponse syncSingleScale(Long configId, String externalScaleId);

    List<SyncLog> getSyncLogs(Long configId, Integer syncType);

    SyncStatisticsResponse getSyncStatistics(Long configId);

    void syncScaleQuestions(String platformCode, String scaleId);

    void scheduleSyncTask(Long configId, Integer intervalMinutes);
}
