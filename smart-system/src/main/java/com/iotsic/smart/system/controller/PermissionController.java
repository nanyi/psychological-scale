package com.iotsic.smart.system.controller;

import com.iotsic.ps.core.entity.Permission;
import com.iotsic.smart.system.mapper.PermissionMapper;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限控制器
 * 获取权限列表、权限详情、权限树结构
 *
 * @author Ryan
 */
@Slf4j
@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionMapper permissionMapper;

    @GetMapping("/list")
    public RestResult<List<Permission>> getAllPermissions() {
        return RestResult.success(permissionMapper.selectList(null));
    }

    @GetMapping("/detail/{id}")
    public RestResult<Permission> getPermissionById(@PathVariable Long id) {
        return RestResult.success(permissionMapper.selectById(id));
    }

    @GetMapping("/tree")
    public RestResult<List<Permission>> getPermissionTree() {
        List<Permission> allPermissions = permissionMapper.selectList(null);
        return RestResult.success(buildTree(allPermissions));
    }

    private List<Permission> buildTree(List<Permission> permissions) {
        return permissions.stream()
                .filter(p -> p.getParentId() == null || p.getParentId() == 0)
                .peek(p -> p.setChildren(getChildren(p.getId(), permissions)))
                .toList();
    }

    private List<Permission> getChildren(Long parentId, List<Permission> allPermissions) {
        return allPermissions.stream()
                .filter(p -> parentId.equals(p.getParentId()))
                .peek(p -> p.setChildren(getChildren(p.getId(), allPermissions)))
                .toList();
    }
}
