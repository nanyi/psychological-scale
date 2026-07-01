package com.iotsic.ps.user.mapper.oauth2;

import com.iotsic.ps.core.entity.OAuth2AccessToken;
import com.iotsic.smart.framework.mybatis.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 访问令牌表
 *
 * @author Ryan
 * @since 2026-04-27 22:19
 */
@Mapper
public interface OAuth2AccessTokenMapper extends BaseMapperPlus<OAuth2AccessToken> {

    /**
     * 根据访问令牌查询
     *
     * @param accessToken 访问令牌
     * @return 访问令牌
     */
    default OAuth2AccessToken selectByAccessToken(String accessToken) {
        return selectOne(OAuth2AccessToken::getAccessToken, accessToken);
    }

}
