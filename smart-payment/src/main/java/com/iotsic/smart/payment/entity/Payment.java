package com.iotsic.smart.payment.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("pay_order")
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity {

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
}
