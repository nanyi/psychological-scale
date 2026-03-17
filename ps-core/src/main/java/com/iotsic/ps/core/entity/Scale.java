package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ps_scale")
public class Scale extends BaseEntity {

    /**
     * 量表编码
     */
    private String scaleCode;

    /**
     * 量表名称
     */
    private String scaleName;

    /**
     * 量表英文名称
     */
    @TableField(value = "scale_name_en")
    private String scaleNameEn;

    /**
     * 分类ID
     */
    @TableField(value = "category_id")
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
     * 量表封面图片
     */
    private String cover;

    /**
     * 量表预计时长（分钟）
     */
    private Integer duration;

    /**
     * 量表问题数量
     */
    private Integer questionCount;

    /**
     * 量表维度数量
     */
    private Integer dimensionCount;

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
     * 注意事项
     */
    private String attention;

    /**
     * 是否免费(0-否,1-是)
     */
    private Integer isFree;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 使用量
     */
    private Integer useCount;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 状态(0-草稿,1-已发布,2-已下架)
     */
    private Integer status;
}
