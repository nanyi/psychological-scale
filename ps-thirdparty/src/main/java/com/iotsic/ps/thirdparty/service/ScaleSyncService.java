package com.iotsic.ps.thirdparty.service;

import com.iotsic.ps.thirdparty.entity.SyncLog;
import com.iotsic.ps.thirdparty.entity.ThirdPartyConfig;

import java.util.List;
import java.util.Map;

public interface ScaleSyncService {

    Map<String, Object> syncScalesFromPlatform(Long configId);

    Map<String, Object> syncSingleScale(Long configId, String externalScaleId);

    List<SyncLog> getSyncLogs(Long configId, Integer syncType);

    Map<String, Object> getSyncStatistics(Long configId);
}
