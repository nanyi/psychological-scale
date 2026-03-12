package com.iotsic.ps.thirdparty.service;

import com.iotsic.ps.thirdparty.dto.PlatformAnswerResponse;
import com.iotsic.ps.thirdparty.dto.PlatformQuestionsResponse;
import com.iotsic.ps.thirdparty.dto.PlatformReportResponse;

import java.util.Map;

public interface ThirdPartyApiService {

    PlatformQuestionsResponse getQuestionsFromPlatform(Long configId, String externalScaleId);

    PlatformAnswerResponse submitAnswersToPlatform(Long configId, Map<String, Object> params);

    PlatformReportResponse getReportFromPlatform(Long configId, String externalRecordId);
}
