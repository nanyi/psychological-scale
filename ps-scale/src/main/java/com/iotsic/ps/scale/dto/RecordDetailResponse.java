package com.iotsic.ps.scale.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 测评记录详情响应DTO
 *
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class RecordDetailResponse {

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 记录编号
     */
    private String recordNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 量表ID
     */
    private Long scaleId;

    /**
     * 量表名称
     */
    private String scaleName;

    /**
     * 总分
     */
    private BigDecimal totalScore;

    /**
     * 状态（0-未完成,1-已完成）
     */
    private Integer status;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 答题时长（秒）
     */
    private Integer duration;

    /**
     * 答案列表
     */
    private List<AnswerDetail> answers;

    @Data
    public static class AnswerDetail {
        private Long questionId;
        private String questionContent;
        private String answer;
        private BigDecimal score;
    }
}
