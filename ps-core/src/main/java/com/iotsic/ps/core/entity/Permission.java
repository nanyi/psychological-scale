package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
public class Permission extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 版本号
     */
    @TableField(value = "version")
    @Version
    private Integer version;
}
