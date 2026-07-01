package com.iotsic.smart.scale.thirdparty.service;

import com.iotsic.smart.scale.thirdparty.dto.PlatformAnswerResponse;
import com.iotsic.smart.scale.thirdparty.dto.PlatformQuestionsResponse;
import com.iotsic.smart.scale.thirdparty.dto.PlatformReportResponse;

import java.util.Map;

public interface ThirdPartyApiService {

    PlatformQuestionsResponse getQuestionsFromPlatform(Long configId, String externalScaleId);

    PlatformAnswerResponse submitAnswersToPlatform(Long configId, Map<String, Object> params);

    PlatformReportResponse getReportFromPlatform(Long configId, String externalRecordId);
}
