package com.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scale_scoring_rule")
public class ScoringRule extends BaseEntity {

    private Long scaleId;

    private Long dimensionId;

    private Integer ruleType;

    private String ruleConfig;

    private BigDecimal minScore;

    private BigDecimal maxScore;

    private BigDecimal weight;

    private String interpretationRule;

    private Integer status;
}
