package com.iotsic.smart.system.service.oauth2;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.iotsic.ps.core.entity.AdminUser;
import com.iotsic.ps.core.enums.UserTypeEnum;
import com.iotsic.smart.framework.common.exception.BusinessException;
import com.iotsic.smart.framework.common.exception.enums.GlobalResultCode;
import com.iotsic.smart.framework.common.utils.BeanUtils;
import com.iotsic.smart.framework.common.utils.DateUtils;
import com.iotsic.smart.framework.security.utils.JwtTokenUtils;
import com.iotsic.smart.framework.tenant.utils.TenantUtils;
import com.iotsic.smart.system.dal.redis.oauth2.OAuth2AccessTokenRedisDAO;
import com.iotsic.smart.system.entity.oauth2.OAuth2AccessToken;
import com.iotsic.smart.system.entity.oauth2.OAuth2Client;
import com.iotsic.smart.system.entity.oauth2.OAuth2RefreshToken;
import com.iotsic.smart.system.mapper.oauth2.OAuth2AccessTokenMapper;
import com.iotsic.smart.system.mapper.oauth2.OAuth2RefreshTokenMapper;
import com.iotsic.smart.system.service.AdminUserService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 访问令牌服务实现类
 *
 * @author Ryan
 * @since 2026-04-27 22:13
 */
@Service
public class OAuth2TokenServiceImpl implements OAuth2TokenService {

    @Resource
    private OAuth2ClientService oauth2ClientService;
    @Resource
    private OAuth2AccessTokenMapper oauth2AccessTokenMapper;
    @Resource
    private OAuth2RefreshTokenMapper oauth2RefreshTokenMapper;

    @Resource
    private OAuth2AccessTokenRedisDAO oauth2AccessTokenRedisDAO;

    @Resource
    @Lazy
    private AdminUserService adminUserService;

    private OAuth2AccessToken createOAuth2AccessToken(OAuth2RefreshToken refreshToken, OAuth2Client client) {
        OAuth2AccessToken accessTokenDO = new OAuth2AccessToken()
                .setAccessToken(JwtTokenUtils.generateToken(refreshToken.getUserId(), null))
                .setUserId(refreshToken.getUserId())
                .setUserType(refreshToken.getUserType())
                .setUserInfo(buildUserInfo(refreshToken.getUserId(), refreshToken.getUserType()))
                .setClientId(client.getClientId())
                .setScopes(refreshToken.getScopes())
                .setRefreshToken(refreshToken.getRefreshToken())
                .setExpiresTime(refreshToken.getExpiresTime());
        // 优先从 refreshToken 获取租户编号，避免 ThreadLocal 被污染时导致 tenantId 为 null
        String tenantId = refreshToken.getTenantId();
        if (tenantId == null) {
            tenantId = TenantUtils.getTenantId();
        }
        accessTokenDO.setTenantId(tenantId);
        oauth2AccessTokenMapper.insert(accessTokenDO);
        // 记录到 Redis 中
        oauth2AccessTokenRedisDAO.set(accessTokenDO);
        return accessTokenDO;
    }

    private OAuth2RefreshToken createOAuth2RefreshToken(Long userId, Integer userType, OAuth2Client client, List<String> scopes) {
        OAuth2RefreshToken refreshToken = new OAuth2RefreshToken()
                .setRefreshToken(JwtTokenUtils.generateRefreshToken(userId, null))
                .setUserId(userId)
                .setUserType(userType)
                .setClientId(client.getClientId()).setScopes(scopes)
                .setExpiresTime(LocalDateTime.now().plusSeconds(client.getRefreshTokenValiditySeconds()));
        oauth2RefreshTokenMapper.insert(refreshToken);
        return refreshToken;
    }

    private OAuth2AccessToken convertToAccessToken(OAuth2RefreshToken refreshToken) {
        OAuth2AccessToken accessToken = BeanUtils.toBean(refreshToken, OAuth2AccessToken.class);
        accessToken.setAccessToken(refreshToken.getRefreshToken());
        TenantUtils.execute(refreshToken.getTenantId(), (Supplier<OAuth2AccessToken>) () -> accessToken.setUserInfo(buildUserInfo(refreshToken.getUserId(), refreshToken.getUserType())));
        return accessToken;
    }

    @Override
    public OAuth2AccessToken createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes) {
        OAuth2Client clientDO = oauth2ClientService.validOAuthClientFromCache(clientId);
        // 创建刷新令牌
        OAuth2RefreshToken refreshTokenDO = createOAuth2RefreshToken(userId, userType, clientDO, scopes);
        // 创建访问令牌
        return createOAuth2AccessToken(refreshTokenDO, clientDO);
    }

    @Override
    public OAuth2AccessToken getAccessToken(String accessToken) {
        OAuth2AccessToken accessTokenDO = oauth2AccessTokenRedisDAO.get(accessToken);
        if (accessTokenDO != null) {
            return accessTokenDO;
        }

        // 获取不到，从 MySQL 中获取访问令牌
        accessTokenDO = oauth2AccessTokenMapper.selectByAccessToken(accessToken);
        if (accessTokenDO == null) {
            // 特殊：从 MySQL 中获取刷新令牌。原因：解决部分场景不方便刷新访问令牌场景
            // 例如说，积木报表只允许传递 token，不允许传递 refresh_token，导致无法刷新访问令牌
            // 再例如说，前端 WebSocket 的 token 直接跟在 url 上，无法传递 refresh_token
            OAuth2RefreshToken refreshTokenDO = oauth2RefreshTokenMapper.selectByRefreshToken(accessToken);
            if (refreshTokenDO != null && !DateUtils.isExpired(refreshTokenDO.getExpiresTime())) {
                accessTokenDO = convertToAccessToken(refreshTokenDO);
            }
        }

        // 如果在 MySQL 存在，则往 Redis 中写入
        if (accessTokenDO != null && !DateUtils.isExpired(accessTokenDO.getExpiresTime())) {
            oauth2AccessTokenRedisDAO.set(accessTokenDO);
        }
        return accessTokenDO;
    }

    @Override
    public OAuth2AccessToken checkAccessToken(String accessToken) {
        OAuth2AccessToken accessTokenDO = getAccessToken(accessToken);
        if (accessTokenDO == null) {
            throw BusinessException.of(GlobalResultCode.UNAUTHORIZED.getCode(), "访问令牌不存在");
        }
        if (DateUtils.isExpired(accessTokenDO.getExpiresTime())) {
            throw BusinessException.of(GlobalResultCode.UNAUTHORIZED.getCode(), "访问令牌已过期");
        }
        return accessTokenDO;
    }

    /**
     * 加载用户信息，方便 {@link com.iotsic.smart.framework.security.dto.LoginUser} 获取到昵称、部门等信息
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @return 用户信息
     */
    private Map<String, String> buildUserInfo(Long userId, Integer userType) {
        if (userId == null || userId <= 0) {
            return Collections.emptyMap();
        }
        if (userType.equals(UserTypeEnum.ADMIN.getCode())) {
            AdminUser user = adminUserService.getUser(userId);
            return MapUtil.builder("nickname", user.getNickname())
                    .put("deptId", StrUtil.toStringOrNull(user.getDepartmentId())).build();
        } else if (userType.equals(UserTypeEnum.MEMBER.getCode())) {
            return Collections.emptyMap();
        }
        throw new IllegalArgumentException("未知用户类型：" + userType);
    }
}
