package com.iotsic.ps.thirdparty.service;

import java.util.Map;

public interface ThirdPartyApiService {

    Map<String, Object> getQuestionsFromPlatform(Long configId, String externalScaleId);

    Map<String, Object> submitAnswersToPlatform(Long configId, Map<String, Object> params);

    Map<String, Object> getReportFromPlatform(Long configId, String externalRecordId);
}
