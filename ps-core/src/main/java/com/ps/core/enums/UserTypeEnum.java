package com.ps.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    PERSONAL(1, "个人用户"),
    ENTERPRISE_ADMIN(2, "企业管理员"),
    ENTERPRISE_USER(3, "企业用户"),
    ADMIN(9, "系统管理员");

    private final Integer code;
    private final String description;

    public static UserTypeEnum of(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserTypeEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}
