package com.iotsic.ps.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.core.entity.Role;
import com.iotsic.ps.user.dto.UserPermissionsResponse;

import java.util.List;

public interface RoleService {

    IPage<Role> getRolePage(Page<Role> page, String roleName, Integer status);

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    Role getRoleByCode(String roleCode);

    List<Role> getUserRoles(Long userId);

    Role createRole(Role role);

    Role updateRole(Long id, Role role);

    void deleteRole(Long id);

    void assignRole(Long userId, Long roleId);

    void removeRole(Long userId, Long roleId);

    List<String> getUserPermissions(Long userId);

    UserPermissionsResponse getUserPermissionsDetail(Long userId);

    List<Long> getPermissionsByRole(Long roleId);

    void assignPermissions(Long roleId, List<Long> permissionIds);
}
