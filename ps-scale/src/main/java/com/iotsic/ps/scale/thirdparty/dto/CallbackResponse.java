package com.iotsic.ps.scale.thirdparty.dto;

import lombok.Data;

/**
 * 回调处理响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class CallbackResponse {

    /**
     * 回调记录ID
     */
    private Long callbackId;

    /**
     * 处理状态：0-待处理 1-成功 2-失败
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 响应消息
     */
    private String message;
}
