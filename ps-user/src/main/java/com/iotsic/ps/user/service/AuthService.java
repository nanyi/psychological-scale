package com.iotsic.ps.user.service;

import com.alibaba.nacos.plugin.auth.api.AuthResult;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.core.entity.OAuth2AccessToken;
import com.iotsic.ps.core.entity.User;
import com.iotsic.ps.user.dto.AuthResultDTO;
import com.iotsic.ps.user.service.oauth2.OAuth2TokenService;
import com.iotsic.smart.framework.common.exception.BusinessException;
import com.iotsic.smart.framework.encrypt.utils.EncryptUtils;
import com.iotsic.smart.framework.security.dto.LoginResult;
import com.iotsic.smart.framework.security.dto.LoginUser;
import com.iotsic.smart.framework.security.utils.SecurityUtils;
import com.iotsic.smart.framework.tenant.constant.TenantConstants;
import com.iotsic.smart.framework.tenant.utils.TenantUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Ryan
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final OAuth2TokenService oAuth2TokenService;

    /**
     * 认证
     *
     * @param tenantId 租户ID
     * @param username 用户名
     * @param password 密码
     * @return 认证结果
     */
    private User authenticate(String tenantId, String username, String password) {
        AtomicReference<User> user = new AtomicReference<>();
        TenantUtils.execute(tenantId, () -> {
            user.set(userService.getUserByUsername(username));
        });
        if (user.get() == null) {
            throw BusinessException.of(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        if (!EncryptUtils.bcryptCheck(password, user.get().getPassword())) {
            throw BusinessException.of(ErrorCodeEnum.PASSWORD_ERROR.getCode(), "密码错误");
        }

        if (user.get().getStatus() != null && user.get().getStatus() == 0) {
            throw BusinessException.of(ErrorCodeEnum.USER_DISABLED.getCode(), "账户已被禁用");
        }
        return user.get();
    }

    /**
     * 登录认证
     *
     * @param tenantId 租户ID
     * @param username 用户名
     * @param password 密码
     * @param loginIp 登录IP
     * @param deviceId 终端设备ID
     * @return 认证结果
     */
    @Transactional
    public AuthResultDTO login(String tenantId, String username, String password, String loginIp, String deviceId) {
        // 1. 执行认证
        User user = authenticate(tenantId, username, password);

        // 2. 更新用户登录信息
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setLastLoginIp(loginIp)
                .setLastLoginTime(LocalDateTime.now())
                .setLoginFailCount(0);
        userService.updateUser(user.getId(), updateUser);

        // 3. TODO: 记录登录日志

        return generateAuthResult(user, deviceId);
    }

    @Transactional
    public AuthResultDTO loginAfterRegister(String tenantId, String username, String password, String loginIp, String deviceId) {
        // 1. 执行认证
        User user = authenticate(tenantId, username, password);

        // 2. 更新用户登录信息
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setLastLoginIp(loginIp)
                .setLastLoginTime(LocalDateTime.now())
                .setLoginFailCount(0);
        userService.updateUser(user.getId(), updateUser);

        return generateAuthResult(user, deviceId);
    }

    /**
     * 退出登录
     */
    public void logout() {
        LoginUser loginUser = SecurityUtils.getCurrentUser();

        // TODO: 记录登出日志

        SecurityUtils.logout();
    }

    /**
     * 生成认证结果
     * @param user 用户
     * @param deviceId 终端设备id
     * @return 认证结果
     */
    private AuthResultDTO generateAuthResult(User user, String deviceId) {
        // 1. 创建登录用户
        LoginUser loginUser = new LoginUser()
                .setTenantId(user.getTenantId())
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setUserType(user.getUserType())
                .setEnterpriseId(user.getEnterpriseId())
                .setDeviceId(deviceId);

        // 2. 生成 Token
        LoginResult loginResult = SecurityUtils.login(loginUser, null);

        // OAuth2AccessToken oAuth2AccessToken = oAuth2TokenService.createAccessToken();

        // 3. 构建返回结果
        return new AuthResultDTO()
                .setToken(loginResult.getAccessToken())
                .setRefreshToken(loginResult.getRefreshToken())
                .setExpiresIn(loginResult.getExpiresIn())
                .setUserId(loginUser.getUserId())
                .setUsername(loginUser.getUsername());
    }

    /**
     * 刷新 Token
     *
     * @param refreshToken 刷新令牌
     * @return 新的认证结果
     */
    @Transactional
    public AuthResultDTO refreshToken(String refreshToken) {
        if (!SecurityUtils.isRefreshToken(refreshToken)) {
            throw BusinessException.of(ErrorCodeEnum.TOKEN_INVALID.getCode(), "无效的刷新令牌");
        }
        // 1. 验证 RefreshToken
        if (!SecurityUtils.validateToken(refreshToken)) {
            throw BusinessException.of(ErrorCodeEnum.TOKEN_INVALID.getCode(),"RefreshToken 已过期");
        }

        // 2. 获取用户名
        Long userId = SecurityUtils.getUserId(refreshToken);
        String username = SecurityUtils.getUsername(refreshToken);

        User user = userService.getUserByUsername(username);

        String deviceId = UUID.randomUUID().toString();

        // 3. 重新生成 Token
        return generateAuthResult(user, deviceId);
    }

}
