package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ps_question")
public class Question extends BaseEntity {

    private Long scaleId;

    private String questionCode;

    private String questionText;

    private Integer questionType;

    private Integer dimensionId;

    private Integer sort;

    private Integer isRequired;

    private String helpText;

    private Integer isReverse;

    private String options;

    private Integer displayType;

    private String logicRule;
}
