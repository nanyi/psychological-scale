package com.iotsic.ps.thirdparty.dto;

import lombok.Data;

/**
 * 第三方配置更新请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ThirdPartyConfigUpdateRequest {

    /**
     * 配置名称
     */
    private String name;

    /**
     * 平台类型
     */
    private Integer platformType;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * 密钥
     */
    private String apiSecret;

    /**
     * 接口地址
     */
    private String endpoint;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;
}
