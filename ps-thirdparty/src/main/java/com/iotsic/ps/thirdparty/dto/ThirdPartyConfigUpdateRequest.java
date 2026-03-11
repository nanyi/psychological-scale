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
     * 配置ID
     */
    private Long id;

    /**
     * AppId
     */
    private String appId;

    /**
     * AppSecret
     */
    private String appSecret;

    /**
     * 状态
     */
    private Integer status;
}
