package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.math.BigDecimal;

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
     * 量表适用人群
     */
    private String targetAudience;

    /**
     * 量表封面图片URL
     */
    private String coverImage;

    /**
     * 指导语
     */
    private String instruction;

    /**
     * 施测时长（分钟）
     */
    private Integer duration;

    /**
     * 量表数据来源类型(1-内置,2-第三方,3-自定义)
     */
    private Integer sourceType;

    /**
     * 量表数据来源ID(第三方量表ID)
     */
    private Long thirdPartyId;

    /**
     * 第三方平台ID
     */
    private Long platformId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 年龄范围最小值
     */
    private Integer ageRangeMin;

    /**
     * 年龄范围最大值
     */
    private Integer ageRangeMax;

    /**
     * 可用性别(1-男,2-女,3-通用)
     */
    private Integer applicableGender;

    /**
     * 状态(0-草稿,1-已发布,2-已下架)
     */
    private Integer status;
}
