package com.iotsic.ps.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("enterprise_quota")
public class EnterpriseQuota extends BaseEntity {

    private Long enterpriseId;

    private Long scaleId;

    private String scaleName;

    private Integer totalQuota;

    private Integer usedQuota;

    private Integer remainingQuota;

    private BigDecimal price;

    private LocalDateTime expireTime;

    private Integer status;
}
