package com.iotsic.ps.scale.thirdparty.dto;

import lombok.Data;

/**
 * 连接测试响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ConnectionTestResponse {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 响应时间（毫秒）
     */
    private Long responseTime;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private String responseData;
}
