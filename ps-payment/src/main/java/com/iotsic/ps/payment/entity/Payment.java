package com.iotsic.ps.payment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ps_payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("order_id")
    private Long orderId;

    @TableField("order_no")
    private String orderNo;

    @TableField("user_id")
    private Long userId;

    @TableField("pay_amount")
    private BigDecimal payAmount;

    @TableField("pay_method")
    private String payMethod;

    @TableField("trade_no")
    private String tradeNo;

    @TableField("transaction_id")
    private String transactionId;

    @TableField("trade_status")
    private String tradeStatus;

    @TableField("pay_time")
    private LocalDateTime payTime;

    @TableField("notify_data")
    private String notifyData;

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
