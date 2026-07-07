package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scale_option_score")
public class OptionScore extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long questionId;

    private String optionValue;

    private BigDecimal score;

    private Integer reverseScore;

    private String dimensionCode;
}
