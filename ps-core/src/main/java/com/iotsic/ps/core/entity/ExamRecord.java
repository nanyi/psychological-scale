package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("ps_exam_record")
public class ExamRecord extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long taskId;

    private Long userId;

    private Long scaleId;

    private Long enterpriseId;

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

    private String dimensionScores;

    private transient Scale scale;

    /**
     * 版本号
     */
    @TableField(value = "version")
    @Version
    private Integer version;
}
