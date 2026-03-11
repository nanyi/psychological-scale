package com.iotsic.ps.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderTypeEnum {

    PURCHASE(1, "购买"),
    VIP(2, "VIP会员"),
    ENTERPRISE(3, "企业团购"),
    GIFT(4, "礼品卡"),
    OTHER(0, "其他");

    private final Integer code;
    private final String description;

    public static OrderTypeEnum of(Integer code) {
        if (code == null) {
            return null;
        }
        for (OrderTypeEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}
