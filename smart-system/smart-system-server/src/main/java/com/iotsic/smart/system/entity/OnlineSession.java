package com.iotsic.smart.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 在线会话实体
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_online_session")
public class OnlineSession extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 设备类型: web,app,miniprogram,pc
     */
    private String deviceType;

    /**
     * 设备唯一标识
     */
    private String deviceId;

    /**
     * 设备名称(可选)
     */
    private String deviceName;

    /**
     * Token版本号
     */
    private Integer tokenVersion;

    /**
     * 访问令牌(加密存储)
     */
    private String accessToken;

    /**
     * 刷新令牌(加密存储)
     */
    private String refreshToken;

    /**
     * OAuth2客户端ID
     */
    private String clientId;

    /**
     * 登录方式: password,oauth2_authorization_code,oauth2_password
     */
    private String loginMethod;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 最后访问时间
     */
    private LocalDateTime lastAccessTime;

    /**
     * 会话过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 状态: 1-在线, 2-离线, 3-已下线, 4-被踢出
     */
    private Integer status;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * User-Agent/客户端信息
     */
    private String userAgent;

    /**
     * 是否记住我: 0-否, 1-是
     */
    private Integer rememberMe;
}
