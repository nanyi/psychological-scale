package com.iotsic.ps.thirdparty.dto;

import lombok.Data;

/**
 * 量表同步请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScaleSyncRequest {

    /**
     * 第三方平台ID
     */
    private Long platformId;

    /**
     * 外部量表ID
     */
    private String externalScaleId;
}
