package com.iotsic.ps.order.dto;

import lombok.Data;

/**
 * 退款拒绝请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class RefundRejectRequest {

    /**
     * 退款ID
     */
    private Long refundId;

    /**
     * 拒绝原因
     */
    private String reason;
}
