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
     * 量表名称（前端传入的字段名）
     */
    private String name;

    /**
     * 量表名称（英文）
     */
    private String nameEn;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 量表适用人群
     */
    private String targetAudience;

    /**
     * 量表描述
     */
    private String description;

    /**
     * 指导语
     */
    private String instruction;

    /**
     * 注意事项
     */
    private String attention;

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
     * 状态(0-草稿,1-已发布,2-已下架)
     */
    private Integer status;
}
