package com.iotsic.smart.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

/**
 * 登录用户身份权限
 *
 * @author Ryan
 * @since 2025-04-02 17:04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoginUser extends com.iotsic.smart.framework.security.dto.LoginUser {

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 授权范围
     */
    private List<String> scopes;

    /**
     * 访问的租户编号（切换租户时使用）
     */
    private String visitTenantId;

    /**
     * 设备标识
     */
    private String deviceId;

    /**
     * 设备类型: web,app,miniprogram,pc
     */
    private String deviceType;

    /**
     * Token版本号
     */
    private Integer tokenVersion;

    /**
     * 是否记住我
     */
    private Boolean rememberMe;

    public Set<String> rolePermission;
}
