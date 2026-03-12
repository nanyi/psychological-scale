package com.iotsic.ps.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户权限响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserPermissionsResponse {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色列表
     */
    private List<String> roles;

    /**
     * 权限列表
     */
    private List<String> permissions;

    /**
     * 是否为管理员
     */
    private Boolean isAdmin;
}
