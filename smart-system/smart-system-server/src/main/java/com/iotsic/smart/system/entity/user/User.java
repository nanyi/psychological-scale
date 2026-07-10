package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.tenant.dal.entity.TenantBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户 实体
 *
 * @author Ryan
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@ToString(callSuper = true)
public class User extends TenantBaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
}
