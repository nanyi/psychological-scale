package com.iotsic.smart.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 登录日志实体
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_login_log")
public class LoginLog extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 日志类型：1-登录，2-注销，3-刷新Token，4-踢出
     */
    private Integer logType;

    /**
     * 链路追踪编号
     */
    private String traceId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 登录结果：1-成功，2-失败
     */
    private Integer result;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 用户IP
     */
    private String userIp;

    /**
     * 浏览器UA
     */
    private String userAgent;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 设备唯一标识
     */
    private String deviceId;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 租户编号
     */
    private Long tenantId;
}
