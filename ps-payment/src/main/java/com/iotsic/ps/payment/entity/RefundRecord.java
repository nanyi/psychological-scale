package com.iotsic.ps.payment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ps_refund")
@EqualsAndHashCode(callSuper = true)
public class RefundRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("payment_id")
    private Long paymentId;

    @TableField("order_id")
    private Long orderId;

    @TableField("order_no")
    private String orderNo;

    @TableField("user_id")
    private Long userId;

    @TableField("refund_amount")
    private BigDecimal refundAmount;

    @TableField("refund_no")
    private String refundNo;

    @TableField("refund_method")
    private String refundMethod;

    @TableField("trade_refund_no")
    private String tradeRefundNo;

    @TableField("refund_status")
    private String refundStatus;

    @TableField("refund_time")
    private LocalDateTime refundTime;

    @TableField("refund_reason")
    private String refundReason;

    @TableField("remark")
    private String remark;

    @TableField("status")
    private Integer status;
}
