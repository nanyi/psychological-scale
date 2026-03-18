package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ps_assessment_task")
public class AssessmentTask extends BaseEntity {

    private String taskNo;

    private Long userId;

    private Long scaleId;

    private Long enterpriseId;

    private Long assignerId;

    private Integer taskType;

    private Integer sourceType;

    private Long sourceId;

    private Integer status;

    private Integer progress;

    private LocalDateTime startTime;

    private LocalDateTime finishTime;

    private LocalDateTime expireTime;
}
