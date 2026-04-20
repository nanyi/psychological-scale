package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class User extends BaseEntity {

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
