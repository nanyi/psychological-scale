package com.iotsic.smart.oms.dto;

import lombok.Data;

/**
 * 退款审批请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class RefundApproveRequest {

    /**
     * 退款ID
     */
    private Long refundId;
}
