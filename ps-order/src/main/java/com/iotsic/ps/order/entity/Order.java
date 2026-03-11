package com.iotsic.ps.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("order_info")
public class Order extends BaseEntity {

    private String orderNo;

    private Long userId;

    private Long scaleId;

    private String scaleName;

    private BigDecimal amount;

    private BigDecimal discountAmount;

    private BigDecimal actualAmount;

    private Integer orderType;

    private Integer orderStatus;

    private String payMethod;

    private String transactionId;

    private LocalDateTime payTime;

    private LocalDateTime expireTime;

    private String remark;

    private String cancelReason;
}
