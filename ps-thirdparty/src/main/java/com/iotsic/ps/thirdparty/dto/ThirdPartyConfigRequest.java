package com.iotsic.ps.thirdparty.dto;

import lombok.Data;

/**
 * 第三方平台配置请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ThirdPartyConfigRequest {

    /**
     * 平台名称
     */
    private String platformName;

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
