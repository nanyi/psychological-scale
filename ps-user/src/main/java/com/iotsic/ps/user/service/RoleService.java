package com.iotsic.ps.user.service;

import com.iotsic.ps.core.entity.Role;
import com.iotsic.ps.user.dto.UserPermissionsResponse;

import java.util.List;

public interface RoleService {

    Role getRoleById(Long id);

    Role getRoleByCode(String roleCode);

    List<Role> getUserRoles(Long userId);

    void assignRole(Long userId, Long roleId);

    void removeRole(Long userId, Long roleId);

    List<String> getUserPermissions(Long userId);

    UserPermissionsResponse getUserPermissionsDetail(Long userId);
}
