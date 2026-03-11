package com.iotsic.ps.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("order_refund")
public class Refund extends BaseEntity {

    private Long orderId;

    private String refundNo;

    private Long userId;

    private BigDecimal refundAmount;

    private Integer refundStatus;

    private String refundReason;

    private String refundMethod;

    private String transactionId;

    private LocalDateTime refundTime;

    private String rejectReason;
}
