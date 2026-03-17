package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 量表更新请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScaleUpdateRequest {

    /**
     * 量表名称
     */
    private String name;

    /**
     * 量表英文名称
     */
    private String nameEn;

    /**
     * 量表描述
     */
    private String description;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 量表封面图片URL
     */
    private String coverImage;

    /**
     * 指导语
     */
    private String instruction;

    /**
     * 适用人群
     */
    private String targetPopulation;

    /**
     * 施测时长（分钟）
     */
    private Integer duration;

    /**
     * 价格
     */
    private java.math.BigDecimal price;

    /**
     * 状态
     */
    private Integer status;
}
