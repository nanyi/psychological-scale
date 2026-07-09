package com.iotsic.smart.system.service.oauth2;

import com.iotsic.smart.system.entity.oauth2.OAuth2AccessToken;
import com.iotsic.smart.system.entity.oauth2.OAuth2RefreshToken;

import java.util.List;

/**
 * 访问令牌服务 接口
 *
 * @author Ryan
 */
public interface OAuth2TokenService {

    /**
     * 创建访问令牌
     * 注意：该流程中，会包含创建刷新令牌的创建
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param scopes 授权范围
     * @return 访问令牌的信息
     */
    OAuth2AccessToken createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes);

    /**
     * 获得访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    OAuth2AccessToken getAccessToken(String accessToken);

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    OAuth2AccessToken checkAccessToken(String accessToken);

    default OAuth2RefreshToken getRefreshToken(String token) {
        return new OAuth2RefreshToken();
    }
}
