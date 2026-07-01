package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import com.iotsic.smart.framework.tenant.dal.entity.TenantBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 管理后台的用户 实体
 *
 * @author Ryan
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class AdminUser extends TenantBaseEntity {

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String phone;

    private String email;

    private Integer gender;

    private LocalDateTime birthday;

    private Integer userType;

    private Long enterpriseId;

    private Long departmentId;

    private Integer status;

    private String lastLoginIp;

    private LocalDateTime lastLoginTime;

    private Integer loginFailCount;

    private LocalDateTime lockUntil;

    /**
     * 版本号
     */
    @TableField(value = "version")
    @Version
    private Integer version;
}
