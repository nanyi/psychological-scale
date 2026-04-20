package com.iotsic.ps.scale.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("ps_report_template")
public class ReportTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("scale_id")
    private Long scaleId;

    @TableField("template_name")
    private String templateName;

    @TableField("template_type")
    private Integer templateType;

    @TableField("template_content")
    private String templateContent;

    @TableField("variables")
    private String variables;

    @TableField("status")
    private Integer status;

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