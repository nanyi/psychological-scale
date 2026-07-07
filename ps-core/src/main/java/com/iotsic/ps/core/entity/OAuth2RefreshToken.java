package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.iotsic.smart.framework.tenant.dal.entity.TenantBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 刷新令牌
 *
 * @author Ryan
 * @since 2026-04-27 22:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_oauth2_refresh_token", autoResultMap = true)
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

}
