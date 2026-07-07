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
@TableName("scale_dimension")
public class Dimension extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long scaleId;

    private String dimensionCode;

    private String dimensionName;

    private String dimensionDesc;

    private Integer sort;

    private String formula;

    private BigDecimal weight;

    private String interpretation;
}
