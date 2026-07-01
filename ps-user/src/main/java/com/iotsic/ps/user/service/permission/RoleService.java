package com.iotsic.ps.user.service.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.core.entity.Role;
import com.iotsic.ps.user.dto.UserPermissionsResponse;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色 Service 接口
 *
 * @author Ryan
 */
public interface RoleService {

    /**
     * 获得角色分页
     *
     * @param page  分页
     * @param roleName 角色名称
     * @param status 状态
     * @return 角色分页
     */
    IPage<Role> getRolePage(Page<Role> page, String roleName, Integer status);

    /**
     * 获得角色列表
     *
     * @return 角色列表
     */
    List<Role> getAllRoles();

    /**
     * 获得角色
     *
     * @param id 角色编号
     * @return 角色
     */
    Role getRoleById(Long id);

    /**
     * 获得角色，从缓存中
     *
     * @param id 角色编号
     * @return 角色
     */
    Role getRoleByIdFromCache(Long id);

    /**
     * 获得角色
     *
     * @param roleCode 角色编码
     * @return 角色
     */
    Role getRoleByCode(String roleCode);

    /**
     * 创建角色
     *
     * @param role 角色
     * @return 角色
     */
    Role createRole(Role role);

    /**
     * 更新角色
     *
     * @param id 角色编号
     * @param role 角色
     * @return 角色
     */
    Role updateRole(Long id, Role role);

    /**
     * 删除角色
     *
     * @param id 角色编号
     */
    void deleteRole(Long id);

    /**
     * 获得角色列表
     *
     * @param ids 角色编号数组
     * @return 角色列表
     */
    List<Role> getRoleList(Collection<Long> ids);

    /**
     * 获得角色数组，从缓存中
     *
     * @param ids 角色编号数组
     * @return 角色数组
     */
    List<Role> getRoleListFromCache(Collection<Long> ids);

    /**
     * 获得用户拥有的角色列表
     *
     * @param userId 用户编号
     * @return 角色列表
     */
    List<Role> getUserRoles(Long userId);

    /**
     * 获得用户拥有的角色编号集合
     *
     * @param userId 用户编号
     * @return 角色编号集合
     */
    Set<Long> getUserRoleIdListByUserId(Long userId);

    /**
     * 获得用户拥有的角色编号集合，从缓存中获取
     *
     * @param userId 用户编号
     * @return 角色编号集合
     */
    Set<Long> getUserRoleIdListByUserIdFromCache(Long userId);

    /**
     * 分配用户角色
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     */
    void assignRole(Long userId, Long roleId);

    /**
     * 取消用户角色
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     */
    void removeRole(Long userId, Long roleId);

    /**
     * 获得用户拥有的权限
     *
     * @param userId 用户编号
     * @return 权限
     */
    List<String> getUserPermissions(Long userId);

    /**
     * 获得用户拥有的权限详情
     *
     * @param userId 用户编号
     * @return 权限详情
     */
    UserPermissionsResponse getUserPermissionsDetail(Long userId);

    /**
     * 获得角色拥有的权限编号集合
     *
     * @param roleId 角色编号
     * @return 权限编号集合
     */
    List<Long> getPermissionsByRole(Long roleId);

    /**
     * 角色分配权限
     *
     * @param roleId 角色编号
     * @param permissionIds 权限编号集合
     */
    void assignPermissions(Long roleId, List<Long> permissionIds);

    /**
     * 判断角色编号数组中，是否有管理员
     *
     * @param ids 角色编号数组
     * @return 是否有管理员
     */
    boolean hasAnySuperAdmin(Collection<Long> ids);
}
