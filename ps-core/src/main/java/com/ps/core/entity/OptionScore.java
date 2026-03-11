package com.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scale_option_score")
public class OptionScore extends BaseEntity {

    private Long questionId;

    private String optionValue;

    private BigDecimal score;

    private Integer reverseScore;

    private String dimensionCode;
}
