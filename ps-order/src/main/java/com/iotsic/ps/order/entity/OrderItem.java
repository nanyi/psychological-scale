package com.iotsic.ps.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("order_item")
public class OrderItem extends BaseEntity {

    private String orderNo;

    private Long scaleId;

    private String scaleName;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal amount;

    private Integer refundStatus;

    private BigDecimal refundAmount;
}
