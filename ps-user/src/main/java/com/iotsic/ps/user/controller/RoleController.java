package com.iotsic.ps.user.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.core.entity.Role;
import com.iotsic.ps.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{id}")
    public RestResult<Role> getRoleById(@PathVariable Long id) {
        return RestResult.success(roleService.getRoleById(id));
    }

    @GetMapping("/code/{code}")
    public RestResult<Role> getRoleByCode(@PathVariable String code) {
        return RestResult.success(roleService.getRoleByCode(code));
    }

    @GetMapping("/user/{userId}")
    public RestResult<List<Role>> getUserRoles(@PathVariable Long userId) {
        return RestResult.success(roleService.getUserRoles(userId));
    }

    @GetMapping("/permissions/{userId}")
    public RestResult<Map<String, Object>> getUserPermissions(@PathVariable Long userId) {
        List<String> permissions = roleService.getUserPermissions(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("permissions", permissions);
        return RestResult.success(result);
    }

    @PostMapping("/assign")
    public RestResult<Void> assignRole(@RequestParam Long userId, @RequestParam Long roleId) {
        roleService.assignRole(userId, roleId);
        return RestResult.success();
    }

    @DeleteMapping("/remove")
    public RestResult<Void> removeRole(@RequestParam Long userId, @RequestParam Long roleId) {
        roleService.removeRole(userId, roleId);
        return RestResult.success();
    }
}
