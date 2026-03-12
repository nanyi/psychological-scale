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
     * 用户名
     */
    private String username;

    /**
     * 权限列表
     */
    private List<PermissionDTO> permissions;

    /**
     * 权限DTO
     */
    @Data
    public static class PermissionDTO {

        /**
         * 权限ID
         */
        private Long permissionId;

        /**
         * 权限编码
         */
        private String permissionCode;

        /**
         * 权限名称
         */
        private String permissionName;
    }
}
