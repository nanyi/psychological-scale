package com.iotsic.ps.thirdparty.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 平台答案提交响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class PlatformAnswerResponse {

    /**
     * 外部记录编号
     */
    private String externalRecordId;

    /**
     * 提交状态：success/fail
     */
    private String submitStatus;

    /**
     * 提交时间
     */
    private LocalDateTime submitTime;

    /**
     * 响应消息
     */
    private String message;
}
