package com.iotsic.smart.system.controller;

import com.iotsic.ps.core.entity.User;
import com.iotsic.smart.framework.common.result.RestResult;
import com.iotsic.smart.framework.common.utils.http.HttpUtils;
import com.iotsic.smart.framework.common.utils.web.NetUtils;
import com.iotsic.smart.framework.security.dto.LoginResult;
import com.iotsic.smart.framework.security.dto.LoginUser;
import com.iotsic.smart.framework.security.utils.JwtTokenUtils;
import com.iotsic.smart.framework.security.utils.SecurityUtils;
import com.iotsic.smart.framework.tenant.constant.TenantConstants;
import com.iotsic.smart.system.dto.AuthResultDTO;
import com.iotsic.smart.system.dto.OAuth2TokenRequest;
import com.iotsic.smart.system.entity.OnlineSession;
import com.iotsic.smart.system.manager.SessionManager;
import com.iotsic.smart.system.mapper.OnlineSessionMapper;
import com.iotsic.smart.system.service.AuthService;
import com.iotsic.smart.system.service.LoginStrategyService;
import com.iotsic.smart.system.service.UserService;
import com.iotsic.smart.system.service.oauth2.OAuth2TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * OAuth2 控制器
 * 支持授权码模式和密码模式
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Slf4j
@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final AuthService authService;
    private final UserService userService;
    private final OAuth2TokenService oauth2TokenService;
    private final SessionManager sessionManager;
    private final LoginStrategyService loginStrategyService;
    private final OnlineSessionMapper onlineSessionMapper;

    /**
     * OAuth2 授权端点（授权码模式）
     * 用户授权后生成授权码并重定向到客户端回调地址
     */
    @GetMapping("/authorize")
    @Operation(summary = "OAuth2 授权端点")
    public void authorize(
            @Parameter(description = "客户端ID") @RequestParam String clientId,
            @Parameter(description = "回调地址") @RequestParam String redirectUri,
            @Parameter(description = "授权范围") @RequestParam(required = false) String scope,
            @Parameter(description = "随机状态") @RequestParam(required = false) String state,
            @Parameter(description = "设备类型") @RequestParam(required = false, defaultValue = "web") String deviceType,
            @Parameter(description = "设备ID") @RequestParam(required = false) String deviceId,
            @Parameter(description = "响应类型") @RequestParam(required = false, defaultValue = "code") String responseType,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        if (!"code".equals(responseType)) {
            response.sendRedirect(redirectUri + "?error=unsupported_response_type&state=" + (state != null ? state : ""));
            return;
        }

        LoginUser currentUser = SecurityUtils.getCurrentUser();
        if (currentUser == null) {
            response.sendRedirect(redirectUri + "?error=login_required&state=" + (state != null ? state : ""));
            return;
        }

        String authorizationCode = UUID.randomUUID().toString().replace("-", "");

        log.info("OAuth2 授权: clientId={}, userId={}, deviceType={}, code={}",
                clientId, currentUser.getUserId(), deviceType, authorizationCode);

        response.sendRedirect(redirectUri + "?code=" + authorizationCode + "&state=" + (state != null ? state : ""));
    }

    /**
     * OAuth2 Token 端点
     * 支持 authorization_code（授权码模式）和 password（密码模式）
     */
    @PostMapping("/token")
    @Operation(summary = "OAuth2 Token 端点")
    public RestResult<OAuth2TokenVO> token(@RequestBody OAuth2TokenRequest request) {
        String grantType = request.getGrantType();

        if ("authorization_code".equals(grantType)) {
            return handleAuthorizationCodeGrant(request);
        } else if ("password".equals(grantType)) {
            return handlePasswordGrant(request);
        } else if ("refresh_token".equals(grantType)) {
            return handleRefreshTokenGrant(request);
        } else {
            return RestResult.fail(400, "不支持的 grant_type: " + grantType);
        }
    }

    private RestResult<OAuth2TokenVO> handleAuthorizationCodeGrant(OAuth2TokenRequest request) {
        String authorizationCode = request.getCode();
        if (authorizationCode == null || authorizationCode.isEmpty()) {
            return RestResult.fail(400, "授权码不能为空");
        }

        LoginUser currentUser = SecurityUtils.getCurrentUser();
        if (currentUser == null) {
            return RestResult.fail(401, "用户未登录");
        }

        User user = userService.getUserById(currentUser.getUserId());
        if (user == null) {
            return RestResult.fail(404, "用户不存在");
        }

        AuthResultDTO authResult = generateAuthResultWithSession(user, request.getDeviceType(),
                request.getDeviceId(), false);

        OAuth2TokenVO vo = new OAuth2TokenVO();
        vo.setAccessToken(authResult.getToken());
        vo.setRefreshToken(authResult.getRefreshToken());
        vo.setTokenType("Bearer");
        vo.setExpiresIn(authResult.getExpiresIn());

        return RestResult.success(vo);
    }

    private RestResult<OAuth2TokenVO> handlePasswordGrant(OAuth2TokenRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String tenantId = request.getTenantId() != null ? request.getTenantId() : TenantConstants.DEFAULT_TENANT_ID;

        if (username == null || password == null) {
            return RestResult.fail(400, "用户名和密码不能为空");
        }

        String deviceType = request.getDeviceType() != null ? request.getDeviceType() : "web";
        String deviceId = request.getDeviceId() != null ? request.getDeviceId() : UUID.randomUUID().toString();
        Boolean rememberMe = request.getRememberMe() != null ? request.getRememberMe() : false;

        AuthResultDTO authResult = authService.login(tenantId, username, password, NetUtils.getClientIP(), deviceId);

        if (authResult == null) {
            return RestResult.fail(401, "用户名或密码错误");
        }

        User user = userService.getUserByUsername(username);
        if (user != null) {
            saveOnlineSession(user, deviceType, deviceId, "oauth2_password", rememberMe);
        }

        OAuth2TokenVO vo = new OAuth2TokenVO();
        vo.setAccessToken(authResult.getToken());
        vo.setRefreshToken(authResult.getRefreshToken());
        vo.setTokenType("Bearer");
        vo.setExpiresIn(authResult.getExpiresIn());

        return RestResult.success(vo);
    }

    private RestResult<OAuth2TokenVO> handleRefreshTokenGrant(OAuth2TokenRequest request) {
        String refreshToken = request.getRefreshToken();
        if (refreshToken == null || refreshToken.isEmpty()) {
            return RestResult.fail(400, "刷新令牌不能为空");
        }

        AuthResultDTO authResult = authService.refreshToken(refreshToken);
        if (authResult == null) {
            return RestResult.fail(401, "无效的刷新令牌");
        }

        OAuth2TokenVO vo = new OAuth2TokenVO();
        vo.setAccessToken(authResult.getToken());
        vo.setRefreshToken(authResult.getRefreshToken());
        vo.setTokenType("Bearer");
        vo.setExpiresIn(authResult.getExpiresIn());

        return RestResult.success(vo);
    }

    private AuthResultDTO generateAuthResultWithSession(User user, String deviceType, String deviceId, Boolean rememberMe) {
        if (deviceType == null) {
            deviceType = "web";
        }
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString();
        }

        sessionManager.handleExistingSessions(user.getId(), deviceType, deviceId);

        LoginUser loginUser = new LoginUser()
                .setTenantId(user.getTenantId())
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setUserType(user.getUserType())
                .setEnterpriseId(user.getEnterpriseId())
                .setDeviceId(deviceId)
                .setDeviceType(deviceType)
                .setRememberMe(rememberMe);

        Map<String, String> extra = new HashMap<>();
        extra.put("tenantId", loginUser.getTenantId());
        extra.put("username", loginUser.getUsername());
        extra.put("userType", loginUser.getUserType().toString());
        extra.put("deviceId", loginUser.getDeviceId());
        extra.put("deviceType", loginUser.getDeviceType());

        LoginResult loginResult = SecurityUtils.login(JwtTokenUtils.generateToken(loginUser.getUserId(), extra), loginUser.getUserId(), extra);

        saveOnlineSession(user, deviceType, deviceId, "oauth2_authorization_code", rememberMe);

        return new AuthResultDTO()
                .setToken(loginResult.getAccessToken())
                .setRefreshToken(loginResult.getRefreshToken())
                .setExpiresIn(loginResult.getExpiresIn())
                .setUserId(loginUser.getUserId())
                .setUsername(loginUser.getUsername());
    }

    private void saveOnlineSession(User user, String deviceType, String deviceId, String loginMethod, Boolean rememberMe) {
        OnlineSession session = new OnlineSession();
        session.setUserId(user.getId());
        session.setUsername(user.getUsername());
        session.setTenantId(user.getTenantId() != null ? Long.parseLong(user.getTenantId()) : null);
        session.setEnterpriseId(user.getEnterpriseId());
        session.setDepartmentId(user.getDepartmentId());
        session.setDeviceType(deviceType);
        session.setDeviceId(deviceId);
        session.setLoginMethod(loginMethod);
        session.setLoginTime(LocalDateTime.now());
        session.setLastAccessTime(LocalDateTime.now());
        session.setRememberMe(rememberMe != null && rememberMe ? 1 : 0);
        session.setStatus(1);

        onlineSessionMapper.insert(session);
    }

    @Data
    public static class OAuth2TokenVO {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;
    }
}
