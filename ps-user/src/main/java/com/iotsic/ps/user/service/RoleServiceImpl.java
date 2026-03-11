package com.iotsic.ps.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.core.entity.*;
import com.iotsic.ps.user.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    private final UserRoleMapper userRoleMapper;
    private final RolePermissionMapper rolePermissionMapper;

    @Override
    public Role getRoleById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Role getRoleByCode(String roleCode) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getRoleCode, roleCode);
        return roleMapper.selectOne(wrapper);
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        LambdaQueryWrapper<UserRole> urWrapper = new LambdaQueryWrapper<>();
        urWrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoles = userRoleMapper.selectList(urWrapper);

        if (userRoles.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        return roleMapper.selectBatchIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRole(Long userId, Long roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId).eq(UserRole::getRoleId, roleId);
        UserRole exist = userRoleMapper.selectOne(wrapper);

        if (exist == null) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRole(Long userId, Long roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId).eq(UserRole::getRoleId, roleId);
        userRoleMapper.delete(wrapper);
    }

    @Override
    public List<String> getUserPermissions(Long userId) {
        List<Role> roles = getUserRoles(userId);
        if (roles.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());

        LambdaQueryWrapper<RolePermission> rpWrapper = new LambdaQueryWrapper<>();
        rpWrapper.in(RolePermission::getRoleId, roleIds);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rpWrapper);

        if (rolePermissions.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> permIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        LambdaQueryWrapper<Permission> permWrapper = new LambdaQueryWrapper<>();
        permWrapper.in(Permission::getId, permIds);
        List<Permission> permissions = permissionMapper.selectList(permWrapper);

        return permissions.stream()
                .map(Permission::getPermissionCode)
                .distinct()
                .collect(Collectors.toList());
    }
}
