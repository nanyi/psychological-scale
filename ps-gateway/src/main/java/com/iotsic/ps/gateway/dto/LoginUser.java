package com.iotsic.ps.gateway.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 登录用户信息
 *
 * @author Ryan
 * @date 2026-04-13 15:40
 */
@Data
@Accessors(chain = true)
public class LoginUser {

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 用户编号
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
     * 授权范围
     */
    private List<String> scopes;

}
