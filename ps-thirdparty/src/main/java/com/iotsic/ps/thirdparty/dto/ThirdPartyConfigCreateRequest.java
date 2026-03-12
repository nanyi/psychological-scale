package com.iotsic.ps.thirdparty.dto;

import lombok.Data;

/**
 * 第三方配置创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ThirdPartyConfigCreateRequest {

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 平台代码
     */
    private String platformCode;

    /**
     * 平台类型
     */
    private Integer platformType;

    /**
     * AppKey
     */
    private String appKey;

    /**
     * AppSecret
     */
    private String appSecret;

    /**
     * API地址
     */
    private String apiUrl;

    /**
     * 回调地址
     */
    private String callbackUrl;

    /**
     * 配置JSON
     */
    private String configJson;

    /**
     * 同步间隔（分钟）
     */
    private Integer syncInterval;

    /**
     * 描述
     */
    private String description;
}
