package com.iotsic.ps.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ps_refund")
public class Refund extends BaseEntity {

    @TableField("order_no")
    private String orderNo;

    @TableField("payment_id")
    private Long paymentId;

    @TableField("order_item_id")
    private Long orderItemId;

    @TableField("scale_id")
    private Long scaleId;

    @TableField("refund_no")
    private String refundNo;

    @TableField("user_id")
    private Long userId;

    @TableField("refund_amount")
    private BigDecimal refundAmount;

    @TableField("trade_refund_no")
    private String tradeRefundNo;

    @TableField("refund_status")
    private Integer refundStatus;

    private String refundReason;

    @TableField("refund_method")
    private String refundMethod;

    private String transactionId;

    @TableField("refund_time")
    private LocalDateTime refundTime;

    @TableField("refund_reason")
    private String rejectReason;

    @TableField("remark")
    private String remark;

    @TableField("status")
    private Integer status;
}
