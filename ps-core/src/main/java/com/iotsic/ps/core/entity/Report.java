package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ps_report")
public class Report extends BaseEntity {

    private Long recordId;

    @TableField("user_id")
    private Long userId;

    @TableField("scale_id")
    private Long scaleId;

    @TableField("scale_name")
    private String scaleName;

    @TableField("report_no")
    private String reportNo;

    private Integer reportType;

    @TableField("task_id")
    private Long taskId;

    private String reportUrl;

    private String content;

    private Integer generationStatus;

    private LocalDateTime generateStartTime;

    private LocalDateTime generateEndTime;

    private LocalDateTime expireTime;

    private String shareToken;

    private LocalDateTime shareExpireTime;

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
}
