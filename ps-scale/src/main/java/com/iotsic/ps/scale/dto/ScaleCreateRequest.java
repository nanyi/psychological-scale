package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 量表创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScaleCreateRequest {

    /**
     * 量表名称
     */
    private String scaleName;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 量表描述
     */
    private String description;

    /**
     * 指导语
     */
    private String instruction;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 量表名称（前端传入的字段名）
     */
    private String name;

    /**
     * 量表名称（英文）
     */
    private String nameEn;

    /**
     * 封面图片
     */
    private String coverImage;

    /**
     * 预计时长（分钟）
     */
    private Integer duration;

    /**
     * 价格
     */
    private BigDecimal price;
}
