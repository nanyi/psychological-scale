package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("report_info")
public class Report extends BaseEntity {

    private Long recordId;

    private Long userId;

    private Long scaleId;

    private String reportNo;

    private Integer reportType;

    private String reportUrl;

    private String content;

    private Integer status;

    private Integer generationStatus;

    private LocalDateTime generateStartTime;

    private LocalDateTime generateEndTime;

    private LocalDateTime expireTime;

    private String shareToken;

    private LocalDateTime shareExpireTime;
}
