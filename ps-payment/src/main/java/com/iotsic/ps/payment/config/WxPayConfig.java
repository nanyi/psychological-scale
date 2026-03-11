package com.iotsic.ps.payment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wxpay")
public class WxPayConfig {

    private String appId;
    private String mchId;
    private String apiKey;
    private String notifyUrl;
    private String signType = "MD5";
    private String certPath;
    private String timeout = "5000";
    private String connectTimeout = "8000";
}
