package com.iotsic.ps.analysis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ps_norm_data")
public class NormData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("scale_id")
    private Long scaleId;

    @TableField("dimension_id")
    private Long dimensionId;

    @TableField("norm_group")
    private String normGroup;

    @TableField("age_min")
    private Integer ageMin;

    @TableField("age_max")
    private Integer ageMax;

    @TableField("gender")
    private String gender;

    @TableField("sample_size")
    private Integer sampleSize;

    @TableField("mean_score")
    private BigDecimal meanScore;

    @TableField("std_deviation")
    private BigDecimal stdDeviation;

    @TableField("percentile_25")
    private BigDecimal percentile25;

    @TableField("percentile_50")
    private BigDecimal percentile50;

    @TableField("percentile_75")
    private BigDecimal percentile75;

    @TableField("percentile_90")
    private BigDecimal percentile90;

    @TableField("percentile_95")
    private BigDecimal percentile95;

    @TableField("low_score")
    private BigDecimal lowScore;

    @TableField("low_label")
    private String lowLabel;

    @TableField("medium_score")
    private BigDecimal mediumScore;

    @TableField("medium_label")
    private String mediumLabel;

    @TableField("high_score")
    private BigDecimal highScore;

    @TableField("high_label")
    private String highLabel;

    @TableField("source")
    private String source;

    @TableField("remark")
    private String remark;

    @TableField("status")
    private Integer status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}
