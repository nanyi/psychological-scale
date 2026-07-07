package com.iotsic.smart.system.mapper.oauth2;

import com.iotsic.smart.framework.mybatis.mapper.BaseMapperPlus;
import com.iotsic.smart.system.entity.oauth2.OAuth2Client;

/**
 * OAuth2 客户端 Mapper
 *
 * @author Ryan
 */
public interface OAuth2ClientMapper extends BaseMapperPlus<OAuth2Client> {

    default OAuth2Client selectByClientId(String clientId) {
        return selectOne(OAuth2Client::getClientId, clientId);
    }
}
