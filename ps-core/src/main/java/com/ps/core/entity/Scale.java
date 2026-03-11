package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scale_info")
public class Scale extends BaseEntity {

    private String scaleCode;

    private String scaleName;

    private String scaleDesc;

    private Integer category;

    private String cover;

    private Integer dimensionCount;

    private Integer questionCount;

    private Integer estimatedTime;

    private BigDecimal price;

    private Integer ageRangeMin;

    private Integer ageRangeMax;

    private Integer applicableGender;

    private String instructions;

    private String attention;

    private Integer status;

    private Integer isFree;

    private Integer viewCount;

    private Integer useCount;

    private LocalDateTime publishTime;
}
