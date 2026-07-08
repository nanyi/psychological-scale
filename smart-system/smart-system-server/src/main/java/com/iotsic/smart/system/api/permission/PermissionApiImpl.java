package com.iotsic.smart.system.api.permission;

import com.iotsic.smart.system.service.permission.PermissionService;
import com.iotsic.smart.framework.common.result.RestResult;
import com.iotsic.smart.framework.security.dto.LoginUser;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限 API 实现类
 *
 * @author Ryan
 * @since 2026-04-28 14:15
 */
@RestController
@Validated
public class PermissionApiImpl implements PermissionApi {

    @Resource
    private PermissionService permissionService;

    @Override
    public RestResult<LoginUser> getUserByToken(String token) {
        LoginUser loginUser = new LoginUser().setUsername("admin").setUserId(1L);
        return RestResult.success(loginUser);
    }

    @Override
    public RestResult<Boolean> hasAnyPermissions(Long userId, String... permissions) {
        return RestResult.success(permissionService.hasAnyPermissions(userId, permissions));
    }

    @Override
    public RestResult<Boolean> hasAnyRoles(Long userId, String... roles) {
        return RestResult.success(permissionService.hasAnyRoles(userId, roles));
    }
}
