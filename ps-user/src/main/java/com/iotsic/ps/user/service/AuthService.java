package com.iotsic.ps.user.service;

import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.core.entity.User;
import com.iotsic.ps.user.dto.AuthResultDTO;
import com.iotsic.smart.framework.encrypt.utils.EncryptUtils;
import com.iotsic.smart.framework.security.dto.LoginResult;
import com.iotsic.smart.framework.security.dto.LoginUser;
import com.iotsic.smart.framework.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Ryan
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    public User authenticate(String username, String password) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw BusinessException.of(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        if (!EncryptUtils.bcryptCheck(password, user.getPassword())) {
            throw BusinessException.of(ErrorCodeEnum.PASSWORD_ERROR.getCode(), "密码错误");
        }

        if (user.getStatus() != null && user.getStatus() == 0) {
            throw BusinessException.of(ErrorCodeEnum.USER_DISABLED.getCode(), "账户已被禁用");
        }
        return user;
    }

    /**
     * 登录认证
     *
     * @param username 用户名
     * @param password 密码
     * @param loginIp 登录IP
     * @return 认证结果
     */
    @Transactional
    public AuthResultDTO login(String username, String password, String loginIp) {
        // 1. 执行认证
        User user = authenticate(username, password);

        // 2. 更新用户登录信息
        user.setLastLoginIp(loginIp);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLoginFailCount(0);
        // userService.updateById(user);

        // 3. 记录登录日志
        // recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");

        String deviceId = UUID.randomUUID().toString();

        return generateAuthResult(user, deviceId);
    }

    /**
     * 退出登录
     */
    public void logout() {
        LoginUser loginUser = SecurityUtils.getLoginUser();

        // 记录登出日志
        if (loginUser != null) {
            // recordLogininfor(loginUser.getUsername(), Constants.LOGOUT, "退出成功");
        }

        SecurityUtils.logout();
    }

    /**
     * 生成认证结果
     * @param user 用户
     * @param deviceId 终端设备id
     * @return 认证结果
     */
    public AuthResultDTO generateAuthResult(User user, String deviceId) {
        // 1. 创建登录用户
        LoginUser loginUser = new LoginUser()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setUserType(user.getUserType())
                .setNickname(user.getNickname())
                .setEnterpriseId(user.getEnterpriseId());

        // 2. 生成 Token
        LoginResult loginResult = SecurityUtils.login(loginUser, deviceId, null);

        // 4. 构建返回结果
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
