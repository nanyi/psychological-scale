package com.iotsic.ps.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("ps_report_export")
public class ReportExport implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("report_id")
    private Long reportId;

    @TableField("user_id")
    private Long userId;

    @TableField("export_type")
    private Integer exportType;

    @TableField("file_url")
    private String fileUrl;

    @TableField("file_size")
    private Integer fileSize;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
