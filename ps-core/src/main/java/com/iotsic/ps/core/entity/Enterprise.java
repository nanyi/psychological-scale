package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("enterprise_info")
public class Enterprise extends BaseEntity {

    private String enterpriseName;

    private String enterpriseCode;

    private String licenseNo;

    private String contact;

    private String phone;

    private String email;

    private String address;

    private Integer userCount;

    private Integer scaleCount;

    private BigDecimal balance;

    private Integer enterpriseType;

    private Integer status;

    private Long parentId;
}
