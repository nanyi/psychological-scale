package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("exam_record")
public class ExamRecord extends BaseEntity {

    private Long userId;

    private Long scaleId;

    private String recordNo;

    private Integer examStatus;

    private Integer totalScore;

    private BigDecimal score;

    private Integer correctCount;

    private Integer wrongCount;

    private Integer blankCount;

    private Integer answerTime;

    private LocalDateTime startTime;

    private LocalDateTime submitTime;

    private String ipAddress;

    private String deviceInfo;

    private String source;

    private Long enterpriseId;

    private String dimensionScores;

    private transient Scale scale;
}
