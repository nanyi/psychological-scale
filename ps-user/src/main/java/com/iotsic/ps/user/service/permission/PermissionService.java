package com.iotsic.ps.user.service.permission;

import java.util.List;
import java.util.Set;

/**
 * 权限 Service 接口
 *
 * @author Ryan
 * @since 2026-04-28 14:22
 */
public interface PermissionService {

    /**
     * 判断是否有权限，任一一个即可
     *
     * @param userId      用户编号
     * @param permissions 权限
     * @return 是否
     */
    boolean hasAnyPermissions(Long userId, String... permissions);

    /**
     * 判断是否有角色，任一一个即可
     *
     * @param roles 角色数组
     * @return 是否
     */
    boolean hasAnyRoles(Long userId, String[] roles);

    /**
     * 获得权限对应的权限编号数组
     *
     * @param permissionCode 权限标识
     * @return 数组
     */
    List<Long> getPermissionIdListByCodeFromCache(String permissionCode);

    /**
     * 获得拥有指定权限的角色编号数组，从缓存中获取
     *
     * @param permissionId 权限编号
     * @return 角色编号数组
     */
    Set<Long> getPermissionRoleIdListByPermissionIdFromCache(Long permissionId);
}
