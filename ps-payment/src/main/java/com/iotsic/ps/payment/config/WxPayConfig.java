package com.iotsic.ps.payment.config;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Data
@Configuration
@ConfigurationProperties(prefix = "wxpay")
public class WxPayConfig implements WXPayConfig {

    private String appId;
    private String mchId;
    private String apiKey;
    private String notifyUrl;
    private String signType = "MD5";
    private String certPath;
    private String timeout = "5000";
    private String connectTimeout = "8000";

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return apiKey;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return Integer.parseInt(connectTimeout);
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return Integer.parseInt(timeout);
    }
}
