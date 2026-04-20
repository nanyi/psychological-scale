package com.iotsic.ps.scale.thirdparty.service;

public interface ScaleSyncService {

    void syncScalesFromPlatform(Long configId);

    void syncScaleQuestions(String platformCode, String scaleId);

    void scheduleSyncTask(Long configId, Integer intervalMinutes);
}