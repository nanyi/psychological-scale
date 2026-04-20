package com.iotsic.ps.scale.thirdparty.service;

import java.util.Map;

public interface ThirdPartyApiService {

    Map<String, Object> callScaleListApi(String platformCode);

    Map<String, Object> callScaleDetailApi(String platformCode, String scaleId);

    Map<String, Object> callSubmitAnswerApi(String platformCode, Map<String, Object> answerData);
}