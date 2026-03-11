package com.ps.user.service;

import com.ps.core.entity.Role;
import com.ps.core.entity.UserRole;

import java.util.List;

public interface RoleService {

    Role getRoleById(Long id);

    Role getRoleByCode(String roleCode);

    List<Role> getUserRoles(Long userId);

    void assignRole(Long userId, Long roleId);

    void removeRole(Long userId, Long roleId);

    List<String> getUserPermissions(Long userId);
}
