package com.iotsic.smart.system.service.oauth2;

import com.iotsic.ps.common.constant.RedisKeyConstants;
import com.iotsic.smart.framework.common.enums.CommonStatusEnum;
import com.iotsic.smart.framework.common.utils.ObjectUtils;
import com.iotsic.smart.framework.common.utils.SpringUtils;
import com.iotsic.smart.framework.common.utils.StringUtils;
import com.iotsic.smart.system.entity.oauth2.OAuth2Client;
import com.iotsic.smart.system.mapper.oauth2.OAuth2ClientMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * OAuth2.0 Client Service 实现类
 *
 * @author Ryan
 */
@Slf4j
@Service
public class OAuth2ClientServiceImpl implements OAuth2ClientService {

    @Resource
    private OAuth2ClientMapper oauth2ClientMapper;

    /**
     * 获得自身的代理对象
     *
     * @return 自己
     */
    private OAuth2ClientServiceImpl getSelf() {
        return SpringUtils.getBean(getClass());
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.OAUTH_CLIENT, key = "#clientId",            unless = "#result == null")
    public OAuth2Client getOAuth2ClientFromCache(String clientId) {
        return oauth2ClientMapper.selectByClientId(clientId);
    }

    @Override
    public OAuth2Client validOAuthClientFromCache(String clientId, String clientSecret, String authorizedGrantType,                                                  Collection<String> scopes, String redirectUri) {
        // 校验客户端存在、且开启
        OAuth2Client client = getSelf().getOAuth2ClientFromCache(clientId);
        if (client == null) {
            throw exception(OAUTH2_CLIENT_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(client.getStatus())) {
            throw exception(OAUTH2_CLIENT_DISABLE);
        }

        // 校验客户端密钥
        if (StringUtils.isNotEmpty(clientSecret) && ObjectUtils.notEqual(client.getSecret(), clientSecret)) {
            throw exception(OAUTH2_CLIENT_CLIENT_SECRET_ERROR);
        }
        // 校验授权方式
        if (StringUtils.isNotEmpty(authorizedGrantType) && !CollUtil.contains(client.getAuthorizedGrantTypes(), authorizedGrantType)) {
            throw exception(OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS);
        }
        // 校验授权范围
        if (CollUtil.isNotEmpty(scopes) && !CollUtil.containsAll(client.getScopes(), scopes)) {
            throw exception(OAUTH2_CLIENT_SCOPE_OVER);
        }
        // 校验回调地址
        if (StringUtils.isNotEmpty(redirectUri) && !StringUtils.startWithAny(redirectUri, client.getRedirectUris())) {
            throw exception(OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH, redirectUri);
        }
        return client;
    }
}
