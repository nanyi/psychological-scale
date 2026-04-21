package com.iotsic.ps.scale.thirdparty.dto;

import lombok.Data;

import java.util.Map;

/**
 * 第三方答案提交请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class AnswerSubmitRequest {

    /**
     * 第三方配置ID
     */
    private Long configId;

    /**
     * 外部用户ID
     */
    private String externalUserId;

    /**
     * 外部量表ID
     */
    private String externalScaleId;

    /**
     * 答题记录ID
     */
    private Long recordId;

    /**
     * 答案数据（JSON格式）
     */
    private Map<String, Object> answers;

    /**
     * 提交时间
     */
    private String submitTime;
}
