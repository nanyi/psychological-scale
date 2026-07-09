package com.iotsic.ps.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录结果枚举
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Getter
@AllArgsConstructor
public enum LoginResultEnum {

    SUCCESS(1, "成功"),
    FAILURE(2, "失败");

    private final Integer code;
    private final String description;

    public static LoginResultEnum of(Integer code) {
        if (code == null) {
            return null;
        }
        for (LoginResultEnum result : values()) {
            if (result.code.equals(code)) {
                return result;
            }
        }
        return null;
    }
}
