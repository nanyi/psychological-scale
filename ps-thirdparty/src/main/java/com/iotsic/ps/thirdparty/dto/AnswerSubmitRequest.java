package com.iotsic.ps.thirdparty.dto;

import lombok.Data;

/**
 * 答题提交请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class AnswerSubmitRequest {

    /**
     * 第三方平台ID
     */
    private Long platformId;

    /**
     * 外部记录ID
     */
    private String externalRecordId;

    /**
     * 答题数据JSON
     */
    private String answerData;
}
