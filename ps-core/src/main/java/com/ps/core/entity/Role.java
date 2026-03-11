package com.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class Role extends BaseEntity {

    private String roleCode;

    private String roleName;

    private String roleDesc;

    private Integer roleType;

    private Integer status;

    private Integer isSystem;
}
