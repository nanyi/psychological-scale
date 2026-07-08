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

/**
 * 刷新令牌
 *
 * @author Ryan
 * @since 2026-04-27 22:21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_oauth2_refresh_token", autoResultMap = true)
@ToString(callSuper = true)
public class OAuth2RefreshToken extends TenantBaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 是否记住我
     */
    private Boolean rememberMe;

}
