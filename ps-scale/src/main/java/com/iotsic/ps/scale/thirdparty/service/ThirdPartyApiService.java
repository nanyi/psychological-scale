package com.iotsic.ps.scale.thirdparty.service;

import com.iotsic.ps.scale.thirdparty.dto.PlatformAnswerResponse;
import com.iotsic.ps.scale.thirdparty.dto.PlatformQuestionsResponse;
import com.iotsic.ps.scale.thirdparty.dto.PlatformReportResponse;

import java.util.Map;

public interface ThirdPartyApiService {

    PlatformQuestionsResponse getQuestionsFromPlatform(Long configId, String externalScaleId);

    PlatformAnswerResponse submitAnswersToPlatform(Long configId, Map<String, Object> params);

    PlatformReportResponse getReportFromPlatform(Long configId, String externalRecordId);
}
