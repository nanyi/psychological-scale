package com.iotsic.ps.payment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ps_refund_record")
public class RefundRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}
