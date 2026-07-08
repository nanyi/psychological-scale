package com.iotsic.smart.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录策略实体
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_login_strategy")
public class LoginStrategy extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID，0表示全局策略
     */
    private Long tenantId;

    /**
     * 登录策略: 1-单端, 2-多端, 3-同端互斥
     */
    private Integer loginPolicy;

    /**
     * 注销策略: 1-单端, 2-全端, 3-同端
     */
    private Integer logoutPolicy;

    /**
     * 是否允许记住我: 0-否, 1-是
     */
    private Integer allowRememberMe;

    /**
     * 记住我有效期: 30天(秒)
     */
    private Integer rememberMeExpireSeconds;

    /**
     * 离线超时时间(秒)
     */
    private Integer offlineTimeoutSeconds;

    /**
     * AccessToken有效期(秒)
     */
    private Integer accessTokenExpireSeconds;

    /**
     * RefreshToken有效期(秒)
     */
    private Integer refreshTokenExpireSeconds;

    /**
     * 是否启用: 0-禁用, 1-启用
     */
    private Integer isActive;
}
