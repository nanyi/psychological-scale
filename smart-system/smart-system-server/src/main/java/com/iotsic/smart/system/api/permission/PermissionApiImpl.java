package com.iotsic.smart.system.api.permission;

import com.iotsic.ps.core.entity.User;
import com.iotsic.smart.framework.security.dto.LoginUser;
import com.iotsic.smart.system.entity.oauth2.OAuth2AccessToken;
import com.iotsic.smart.system.entity.oauth2.OAuth2RefreshToken;
import com.iotsic.smart.system.service.UserService;
import com.iotsic.smart.system.service.oauth2.OAuth2TokenService;
import com.iotsic.smart.system.service.permission.PermissionService;
import com.iotsic.smart.framework.common.result.RestResult;
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
    @Resource
    private OAuth2TokenService oAuth2TokenService;
    @Resource
    private UserService userService;

    @Override
    public RestResult<LoginUser> getUserByToken(String token) {
        OAuth2AccessToken accessToken = oAuth2TokenService.checkAccessToken(token);
        OAuth2RefreshToken refreshToken = oAuth2TokenService.getRefreshToken(token);
        User user = userService.getUserById(accessToken.getUserId());
        return RestResult.success(buildByUser(user, accessToken, refreshToken.getRememberMe(), user.getTenantId()));
    }

    @Override
    public RestResult<Boolean> hasAnyPermissions(Long userId, String... permissions) {
        return RestResult.success(permissionService.hasAnyPermissions(userId, permissions));
    }

    @Override
    public RestResult<Boolean> hasAnyRoles(Long userId, String... roles) {
        return RestResult.success(permissionService.hasAnyRoles(userId, roles));
    }

    private LoginUser buildByUser(User user, OAuth2AccessToken accessToken, Boolean rememberMe, String visitTenantId) {
        if (user == null) {
            return null;
        }
        return new LoginUser()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setUserType(user.getUserType())
                .setTenantId(user.getTenantId())
                .setEnterpriseId(user.getEnterpriseId())
                .setDeviceId(accessToken.getDeviceId())
                .setDeviceType(accessToken.getDeviceType())
                .setScopes(accessToken.getScopes())
                .setVisitTenantId(visitTenantId)
                .setTokenVersion(accessToken.getTokenVersion())
                .setRememberMe(rememberMe);
    }
}
