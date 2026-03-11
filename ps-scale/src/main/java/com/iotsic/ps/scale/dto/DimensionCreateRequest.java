package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 维度创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class DimensionCreateRequest {

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 维度名称
     */
    private String dimensionName;

    /**
     * 维度描述
     */
    private String description;
}
