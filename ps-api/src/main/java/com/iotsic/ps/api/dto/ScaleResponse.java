package com.iotsic.ps.api.dto;

import lombok.Data;

/**
 * 量表基本信息响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScaleResponse {

    /**
     * 量表ID
     */
    private Long id;

    /**
     * 量表名称
     */
    private String scaleName;

    /**
     * 量表编码
     */
    private String scaleCode;

    /**
     * 量表分类ID
     */
    private Long categoryId;

    /**
     * 量表分类名称
     */
    private String categoryName;

    /**
     * 量表描述
     */
    private String description;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;
}
