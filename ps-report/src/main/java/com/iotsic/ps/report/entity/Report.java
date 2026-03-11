package com.iotsic.ps.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ps_report")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("report_no")
    private String reportNo;

    @TableField("task_id")
    private Long taskId;

    @TableField("user_id")
    private Long userId;

    @TableField("scale_id")
    private Long scaleId;

    @TableField("scale_name")
    private String scaleName;

    @TableField("total_score")
    private BigDecimal totalScore;

    @TableField("dimension_scores")
    private String dimensionScores;

    @TableField("result_level")
    private String resultLevel;

    @TableField("conclusion")
    private String conclusion;

    @TableField("suggestions")
    private String suggestions;

    @TableField("report_content")
    private String reportContent;

    @TableField("source_type")
    private Integer sourceType;

    @TableField("third_party_report")
    private String thirdPartyReport;

    @TableField("status")
    private Integer status;

    @TableField("generate_time")
    private LocalDateTime generateTime;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;

    @TableField("version")
    @Version
    private Integer version;
}
