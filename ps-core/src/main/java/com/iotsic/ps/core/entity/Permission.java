package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
public class Permission extends BaseEntity {

    private String permissionCode;

    private String permissionName;

    private String permissionDesc;

    private Integer permissionType;

    private String resource;

    private String method;

    private Long parentId;

    private Integer sortOrder;

    private String icon;

    private String path;

    private transient List<Permission> children;
}
