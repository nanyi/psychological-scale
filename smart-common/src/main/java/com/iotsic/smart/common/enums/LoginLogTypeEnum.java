package com.iotsic.smart.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录日志类型枚举
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Getter
@AllArgsConstructor
public enum LoginLogTypeEnum {

    LOGIN(1, "登录"),
    LOGOUT(2, "注销"),
    REFRESH_TOKEN(3, "刷新Token"),
    KICK(4, "踢出");

    private final Integer code;
    private final String description;

    public static LoginLogTypeEnum of(Integer code) {
        if (code == null) {
            return null;
        }
        for (LoginLogTypeEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}
