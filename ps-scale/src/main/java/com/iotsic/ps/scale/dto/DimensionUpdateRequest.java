package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 维度更新请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class DimensionUpdateRequest {

    /**
     * 维度名称
     */
    private String name;

    /**
     * 维度描述
     */
    private String description;

    /**
     * 排序号
     */
    private Integer sortOrder;
}
