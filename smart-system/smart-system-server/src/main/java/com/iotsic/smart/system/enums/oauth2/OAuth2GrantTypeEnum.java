package com.iotsic.smart.system.enums.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OAuth2 授权类型（模式）的枚举
 *
 * @author Ryan
 */
@Getter
@AllArgsConstructor
public enum OAuth2GrantTypeEnum {

    /**
     * 密码模式
     */
    PASSWORD("password"),
    /**
     * 授权码模式
     */
    AUTHORIZATION_CODE("authorization_code"),
    /**
     * 简化模式
     */
    IMPLICIT("implicit"),
    /**
     * 客户端模式
     */
    CLIENT_CREDENTIALS("client_credentials"),
    /**
     * 刷新模式
     */
    REFRESH_TOKEN("refresh_token");

    private final String grantType;

    public static OAuth2GrantTypeEnum ofGrantType(String grantType) {
        if (grantType != null) {
        for (OAuth2GrantTypeEnum type : values()) {
            if (type.grantType.equals(grantType)) {
                return type;
            }
        }
        }
        return null;
    }
}
