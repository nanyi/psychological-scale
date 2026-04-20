package com.iotsic.ps.scale.thirdparty.service;

import com.iotsic.ps.scale.thirdparty.entity.ThirdPartyCallback;

public interface CallbackService {

    void processCallback(Long configId, String callbackType, String data);

    void saveCallbackRecord(ThirdPartyCallback callback);
}