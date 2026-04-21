package com.iotsic.ps.thirdparty.service;

import com.iotsic.ps.thirdparty.dto.SyncResultResponse;
import com.iotsic.ps.thirdparty.dto.SyncStatisticsResponse;
import com.iotsic.ps.thirdparty.entity.SyncLog;
import com.iotsic.ps.thirdparty.entity.ThirdPartyConfig;

import java.util.List;

public interface ScaleSyncService {

    SyncResultResponse syncScalesFromPlatform(Long configId);

    SyncResultResponse syncSingleScale(Long configId, String externalScaleId);

    List<SyncLog> getSyncLogs(Long configId, Integer syncType);

    SyncStatisticsResponse getSyncStatistics(Long configId);
}
