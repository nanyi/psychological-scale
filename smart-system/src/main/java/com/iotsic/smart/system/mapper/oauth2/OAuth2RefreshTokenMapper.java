package com.iotsic.smart.system.mapper.oauth2;

import com.iotsic.smart.system.entity.oauth2.OAuth2RefreshToken;
import com.iotsic.smart.framework.mybatis.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 刷新令牌表
 *
 * @author Ryan
 * @since 2026-04-27 22:20
 */
@Mapper
public interface OAuth2RefreshTokenMapper extends BaseMapperPlus<OAuth2RefreshToken> {

    /**
     * 根据刷新令牌查询
     *
     * @param accessToken 刷新令牌
     * @return 刷新令牌信息
     */
     default OAuth2RefreshToken selectByRefreshToken(String accessToken) {
         return selectOne(OAuth2RefreshToken::getRefreshToken, accessToken);
     }
}
