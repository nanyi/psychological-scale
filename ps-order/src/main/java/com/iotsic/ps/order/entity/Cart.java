package com.iotsic.ps.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.ps.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("order_cart")
public class Cart extends BaseEntity {

    private Long userId;

    private Long scaleId;

    private String scaleName;

    private String cover;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal totalAmount;
}
