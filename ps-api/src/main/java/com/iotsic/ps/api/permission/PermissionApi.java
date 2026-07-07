package com.iotsic.ps.api.permission;

import com.iotsic.ps.api.config.FeignConfig;
import com.iotsic.ps.api.enums.ApiConstants;
import com.iotsic.smart.framework.common.result.RestResult;
import com.iotsic.smart.framework.security.api.RemoteSecurityApi;
import com.iotsic.smart.framework.security.dto.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 权限 API
 *
 * @author Ryan
 * @since 2026-04-28 14:08
 */
@FeignClient(name = ApiConstants.SYSTEM_NAME, contextId = "permissionApi", configuration = FeignConfig.class)
@Tag(name = "RPC 服务 - 权限")
public interface PermissionApi extends RemoteSecurityApi {

    @Override
    @PostMapping(ApiConstants.PREFIX + "/security/token-user")
    RestResult<LoginUser> getUserByToken(String token);

    @Override
    @GetMapping(ApiConstants.PREFIX + "/security/has-any-permissions")
    RestResult<Boolean> hasAnyPermissions(Long userId, String... permissions) ;

    @Override
    @GetMapping( ApiConstants.PREFIX + "/security/has-any-roles")
    RestResult<Boolean> hasAnyRoles(Long userId, String... roles);
}
