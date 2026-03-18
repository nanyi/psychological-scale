package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 退款创建响应DTO
 * 
 * @author Ryan
 * @since 2026-03-11
 */
@Data
public class RefundCreateResponse {

    /**
     * 退款记录ID
     */
    private Long refundId;

    /**
     * 退款单号
     */
    private String refundNo;
}
