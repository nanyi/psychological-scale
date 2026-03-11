package com.ps.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {

    NORMAL(0, "Normal"),
    DISABLED(1, "Disabled"),
    DELETED(2, "Deleted");

    private final Integer code;
    private final String description;

    public static StatusEnum of(Integer code) {
        if (code == null) {
            return null;
        }
        for (StatusEnum status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}
