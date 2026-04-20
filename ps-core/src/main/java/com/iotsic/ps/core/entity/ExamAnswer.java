package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ps_answer_record")
public class ExamAnswer extends BaseEntity {

    private Long recordId;

    private Long questionId;

    private String questionCode;

    private String answer;

    private Integer score;

    private Integer sort;

    private LocalDateTime answerTime;
}
