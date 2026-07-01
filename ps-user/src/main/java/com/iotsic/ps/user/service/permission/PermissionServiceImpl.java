package com.iotsic.ps.user.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.iotsic.ps.common.constant.RedisKeyConstants;
import com.iotsic.ps.core.entity.Permission;
import com.iotsic.ps.core.entity.Role;
import com.iotsic.ps.core.entity.RolePermission;
import com.iotsic.ps.user.mapper.PermissionMapper;
import com.iotsic.ps.user.mapper.RolePermissionMapper;
import com.iotsic.smart.framework.common.enums.CommonStatusEnum;
import com.iotsic.smart.framework.common.utils.CollectionUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 权限 Service 实现类
 *
 * @author Ryan
 * @since 2026-04-28 14:25
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private RoleService roleService;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 获得自身的代理对象，解决缓存生效问题
     *
     * @return 自己
     */
    private PermissionService getSelf() {
        return SpringUtil.getBean(getClass());
    }

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        // 获得当前登录的角色。如果为空，说明没有权限
        List<Role> roles = getEnableUserRoleListByUserIdFromCache(userId);
        if (CollUtil.isEmpty(roles)) {
            return false;
        }

        // 情况一：遍历判断每个权限，如果有一满足，说明有权限
        for (String permission : permissions) {
            if (hasAnyPermission(roles, permission)) {
                return true;
            }
        }

        // 情况二：如果是超管，也说明有权限
        return roleService.hasAnySuperAdmin(CollectionUtils.convertSet(roles, Role::getId));
    }

    /**
     * 判断指定角色，是否拥有该 permission 权限
     *
     * @param roles 指定角色数组
     * @param permission 权限标识
     * @return 是否拥有
     */
    private boolean hasAnyPermission(List<Role> roles, String permission) {
        List<Long> permissionIds = getSelf().getPermissionIdListByCodeFromCache(permission);
        // 采用严格模式，如果权限找不到对应的 Menu 的话，也认为没有权限
        if (CollUtil.isEmpty(permissionIds)) {
            return false;
        }

        // 判断是否有权限
        Set<Long> roleIds = CollectionUtils.convertSet(roles, Role::getId);
        for (Long permissionId : permissionIds) {
            // 获得拥有该菜单的角色编号集合
            Set<Long> permissionRoleIds = getSelf().getPermissionRoleIdListByPermissionIdFromCache(permissionId);
            // 如果有交集，说明有权限
            if (CollUtil.containsAny(permissionRoleIds, roleIds)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAnyRoles(Long userId, String[] roles) {
        // 获得当前登录的角色。如果为空，说明没有权限
        List<Role> roleList = getEnableUserRoleListByUserIdFromCache(userId);
        if (CollectionUtils.isEmpty(roleList)) {
            return false;
        }

        // 判断是否有角色
        Set<String> userRoles = CollectionUtils.convertSet(roleList, Role::getRoleCode);
        return CollectionUtils.containsAny(userRoles, Sets.newHashSet(roles));
    }

    @Override
    @Cacheable(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST, key = "#permissionCode")
    public List<Long> getPermissionIdListByCodeFromCache(String permissionCode) {
        List<Permission> permissionList = permissionMapper.selectListByCode(permissionCode);
        return CollectionUtils.convertList(permissionList, Permission::getId);
    }

    @Override
    @Cacheable(value = RedisKeyConstants.MENU_ROLE_ID_LIST, key = "#permissionId")
    public Set<Long> getPermissionRoleIdListByPermissionIdFromCache(Long permissionId) {
        return CollectionUtils.convertSet(rolePermissionMapper.selectListByPermissionId(permissionId), RolePermission::getRoleId);
    }

    /**
     * 获得用户拥有的角色，并且这些角色是开启状态的
     *
     * @param userId 用户编号
     * @return 用户拥有的角色
     */
    @VisibleForTesting
    List<Role> getEnableUserRoleListByUserIdFromCache(Long userId) {
        // 获得用户拥有的角色编号
        Set<Long> roleIds = roleService.getUserRoleIdListByUserIdFromCache(userId);
        // 获得角色数组，并移除被禁用的
        List<Role> roles = roleService.getRoleListFromCache(roleIds);
        roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus()));
        return roles;
    }
}
