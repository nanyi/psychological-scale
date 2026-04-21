package com.iotsic.ps.thirdparty.service;

import com.iotsic.ps.thirdparty.entity.ThirdPartyCallback;

import java.util.List;
import java.util.Map;

public interface CallbackService {

    Map<String, Object> handleReportCallback(Long configId, Map<String, Object> params);

    ThirdPartyCallback getCallbackById(Long id);

    List<ThirdPartyCallback> getCallbacksByRecordId(String externalRecordId);

    void retryCallback(Long callbackId);
}
