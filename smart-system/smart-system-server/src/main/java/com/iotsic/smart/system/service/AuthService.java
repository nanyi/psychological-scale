package com.iotsic.smart.system.service;

import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.enums.LoginLogTypeEnum;
import com.iotsic.ps.common.enums.LoginResultEnum;
import com.iotsic.ps.core.entity.User;
import com.iotsic.ps.core.enums.UserTypeEnum;
import com.iotsic.smart.framework.common.exception.BusinessException;
import com.iotsic.smart.framework.common.utils.DateUtils;
import com.iotsic.smart.framework.common.utils.web.NetUtils;
import com.iotsic.smart.framework.encrypt.utils.EncryptUtils;
import com.iotsic.smart.framework.security.dto.LoginResult;
import com.iotsic.smart.framework.security.dto.LoginUser;
import com.iotsic.smart.framework.security.utils.JwtTokenUtils;
import com.iotsic.smart.framework.security.utils.SecurityUtils;
import com.iotsic.smart.framework.tenant.utils.TenantUtils;
import com.iotsic.smart.system.dto.AuthResultDTO;
import com.iotsic.smart.system.dto.LoginLogCreateRequest;
import com.iotsic.smart.system.entity.oauth2.OAuth2AccessToken;
import com.iotsic.smart.system.enums.oauth2.OAuth2ClientConstants;
import com.iotsic.smart.system.service.oauth2.OAuth2TokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    private final LoginLogService loginLogService;

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
        try {
            // 1. 执行认证
            User user = authenticate(tenantId, username, password);

            // 2. 更新用户登录信息
            User updateUser = new User()
                    .setId(user.getId())
                    .setLastLoginIp(loginIp)
                    .setLastLoginTime(LocalDateTime.now());
            userService.updateUser(user.getId(), updateUser);

            // 3. 记录成功登录日志
            logLoginSuccess(tenantId, user, loginIp, deviceId);

            return generateAuthResult(user, deviceId);
        } catch (BusinessException e) {
            // 记录失败登录日志
            logLoginFailure(tenantId, username, loginIp, deviceId, e.getMessage());
            throw e;
        }
    }

    @Transactional
    public AuthResultDTO loginAfterRegister(String tenantId, String username, String password, String loginIp, String deviceId) {
        // 1. 执行认证
        User user = authenticate(tenantId, username, password);

        // 2. 更新用户登录信息
        User updateUser = new User()
                .setId(user.getId())
                .setLastLoginIp(loginIp)
                .setLastLoginTime(LocalDateTime.now())
                .setLoginFailCount(0);
        userService.updateUser(user.getId(), updateUser);

        // 3. 记录成功登录日志
        logLoginSuccess(tenantId, user, loginIp, deviceId);

        return generateAuthResult(user, deviceId);
    }

    /**
     * 退出登录
     */
    public void logout(String token) {
        LoginUser loginUser = SecurityUtils.getUserFromToken(token);
        if (loginUser != null) {
            // 记录登出日志
            logLogout(loginUser);
        }
        SecurityUtils.logout(token);
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

        OAuth2AccessToken oAuth2AccessToken = oAuth2TokenService.createAccessToken(user.getId(), UserTypeEnum.ADMIN.getCode(), OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);

        // 2. 生成 Token
        Map<String, String> extra = new HashMap<>();
        extra.put("tenantId", loginUser.getTenantId());
        extra.put("username", loginUser.getUsername());
        extra.put("userType", loginUser.getUserType().toString());
        extra.put("deviceId", loginUser.getDeviceId());
        LoginResult loginResult = SecurityUtils.login(oAuth2AccessToken.getAccessToken(), loginUser.getUserId(), extra);

        oAuth2AccessToken
                .setExpiresTime(DateUtils.parseDateTime(loginResult.getExpiresIn()))
                .setUserType(user.getUserType());

        // 3. 构建返回结果
        return new AuthResultDTO()
                .setToken(oAuth2AccessToken.getAccessToken())
                .setRefreshToken(oAuth2AccessToken.getRefreshToken())
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
        Long userId = SecurityUtils.getUserIdFromToken(refreshToken);
        String username = getUsername(refreshToken);

        User user = userService.getUserByUsername(username);

        String deviceId = UUID.randomUUID().toString();

        // 3. 记录刷新Token日志
        logRefreshToken(user, deviceId);

        // 4. 重新生成 Token
        return generateAuthResult(user, deviceId);
    }

    /**
     * 记录成功登录日志
     */
    private void logLoginSuccess(String tenantId, User user, String loginIp, String deviceId) {
        LoginLogCreateRequest request = new LoginLogCreateRequest();
        request.setLogType(LoginLogTypeEnum.LOGIN.getCode());
        request.setUserId(user.getId());
        request.setUserType(user.getUserType());
        request.setUsername(user.getUsername());
        request.setResult(LoginResultEnum.SUCCESS.getCode());
        request.setUserIp(loginIp);
        request.setUserAgent(NetUtils.getUserAgent());
        request.setDeviceId(deviceId);
        if (user.getTenantId() != null) {
            request.setTenantId(Long.parseLong(user.getTenantId()));
        }
        loginLogService.logLogin(request);
    }

    /**
     * 记录失败登录日志
     */
    private void logLoginFailure(String tenantId, String username, String loginIp, String deviceId, String failReason) {
        LoginLogCreateRequest request = new LoginLogCreateRequest();
        request.setLogType(LoginLogTypeEnum.LOGIN.getCode());
        request.setUsername(username);
        request.setResult(LoginResultEnum.FAILURE.getCode());
        request.setFailReason(failReason);
        request.setUserIp(loginIp);
        request.setUserAgent(NetUtils.getUserAgent());
        request.setDeviceId(deviceId);
        request.setTenantId(Long.parseLong(tenantId));
        loginLogService.logLogin(request);
    }

    /**
     * 记录登出日志
     */
    private void logLogout(LoginUser loginUser) {
        LoginLogCreateRequest request = new LoginLogCreateRequest();
        request.setLogType(LoginLogTypeEnum.LOGOUT.getCode());
        request.setUserId(loginUser.getUserId());
        request.setUserType(loginUser.getUserType());
        request.setUsername(loginUser.getUsername());
        request.setResult(LoginResultEnum.SUCCESS.getCode());
        request.setUserIp(NetUtils.getClientIP());
        request.setUserAgent(NetUtils.getUserAgent());
        request.setDeviceId(loginUser.getDeviceId());
        if (loginUser.getTenantId() != null) {
            request.setTenantId(Long.parseLong(loginUser.getTenantId()));
        }
        loginLogService.logLogin(request);
    }

    /**
     * 记录刷新Token日志
     */
    private void logRefreshToken(User user, String deviceId) {
        LoginLogCreateRequest request = new LoginLogCreateRequest();
        request.setLogType(LoginLogTypeEnum.REFRESH_TOKEN.getCode());
        request.setUserId(user.getId());
        request.setUserType(user.getUserType());
        request.setUsername(user.getUsername());
        request.setResult(LoginResultEnum.SUCCESS.getCode());
        request.setUserIp(NetUtils.getClientIP());
        request.setUserAgent(NetUtils.getUserAgent());
        request.setDeviceId(deviceId);
        if (user.getTenantId() != null) {
            request.setTenantId(Long.parseLong(user.getTenantId()));
        }
        loginLogService.logLogin(request);
    }

    /**
     * 从 Token 中获取用户名
     * @param token 待解析的 Token
     * @return 用户名
     */
    public String getUsername(String token) {
        Claims claims = JwtTokenUtils.parseToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get("username", String.class);
    }
}
