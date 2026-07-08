package com.iotsic.smart.system.entity.oauth2;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.iotsic.smart.framework.tenant.dal.entity.TenantBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 访问令牌表
 *
 * @author Ryan
 * @since 2026-04-27 22:21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_oauth2_access_token", autoResultMap = true)
@ToString(callSuper = true)
public class OAuth2AccessToken extends TenantBaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 用户信息
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, String> userInfo;

    /**
     * 客户端编号
     */
    private String clientId;

    /**
     * 授权范围
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

    /**
     * 设备类型: web,app,miniprogram,pc
     */
    private String deviceType;

    /**
     * 设备唯一标识
     */
    private String deviceId;

    /**
     * Token版本号
     */
    private Integer tokenVersion;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 最后访问时间
     */
    private LocalDateTime lastAccessTime;

    /**
     * 状态: 1-正常, 2-离线, 3-已注销, 4-被踢出
     */
    private Integer status;
}
