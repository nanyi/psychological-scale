package com.iotsic.ps.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.core.entity.Role;
import com.iotsic.ps.user.dto.UserPermissionsResponse;
import com.iotsic.ps.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 * 负责角色的CRUD和权限分配请求
 *
 * @author Ryan
 * @since 2026-03-18
 */
@Slf4j
@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 获取角色列表（分页）
     */
    @GetMapping("/list")
    public RestResult<IPage<Role>> getRoleList(PageRequest request,
                                                @RequestParam(value = "roleName", required = false) String roleName,
                                                @RequestParam(value = "status", required = false) Integer status) {
        Page<Role> page = new Page<>(request.getCurrent() != null ? request.getCurrent() : 1, 
                                     request.getSize() != null ? request.getSize() : 10);
        IPage<Role> result = roleService.getRolePage(page, roleName, status);
        return RestResult.success(result);
    }

    /**
     * 获取所有角色（不分页）
     */
    @GetMapping("/all")
    public RestResult<List<Role>> getAllRoles() {
        return RestResult.success(roleService.getAllRoles());
    }

    /**
     * 根据ID获取角色
     */
    @GetMapping("/detail/{id}")
    public RestResult<Role> getRoleById(@PathVariable Long id) {
        return RestResult.success(roleService.getRoleById(id));
    }

    /**
     * 根据编码获取角色
     */
    @GetMapping("/by-code/{code}")
    public RestResult<Role> getRoleByCode(@PathVariable String code) {
        return RestResult.success(roleService.getRoleByCode(code));
    }

    /**
     * 获取用户角色列表
     */
    @GetMapping("/by-user/{userId}")
    public RestResult<List<Role>> getUserRoles(@PathVariable Long userId) {
        return RestResult.success(roleService.getUserRoles(userId));
    }

    /**
     * 创建角色
     */
    @PostMapping("/create")
    public RestResult<Role> createRole(@RequestBody Role role) {
        Role result = roleService.createRole(role);
        return RestResult.success("角色创建成功", result);
    }

    /**
     * 更新角色
     */
    @PutMapping("/update/{id}")
    public RestResult<Void> updateRole(@PathVariable Long id, @RequestBody Role role) {
        roleService.updateRole(id, role);
        return RestResult.success();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/delete/{id}")
    public RestResult<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return RestResult.success();
    }

    /**
     * 获取用户权限
     */
    @GetMapping("/permissions/{userId}")
    public RestResult<UserPermissionsResponse> getUserPermissions(@PathVariable Long userId) {
        return RestResult.success(roleService.getUserPermissionsDetail(userId));
    }

    /**
     * 获取角色权限列表
     */
    @GetMapping("/permissions/role/{roleId}")
    public RestResult<List<Long>> getPermissionsByRole(@PathVariable Long roleId) {
        return RestResult.success(roleService.getPermissionsByRole(roleId));
    }

    /**
     * 分配角色权限
     */
    @PostMapping("/permissions/{roleId}")
    public RestResult<Void> assignPermissions(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        roleService.assignPermissions(roleId, permissionIds);
        return RestResult.success();
    }

    /**
     * 分配用户角色
     */
    @PostMapping("/assign")
    public RestResult<Void> assignRole(@RequestParam Long userId, @RequestParam Long roleId) {
        roleService.assignRole(userId, roleId);
        return RestResult.success();
    }

    /**
     * 移除用户角色
     */
    @DeleteMapping("/remove")
    public RestResult<Void> removeRole(@RequestParam Long userId, @RequestParam Long roleId) {
        roleService.removeRole(userId, roleId);
        return RestResult.success();
    }
}
