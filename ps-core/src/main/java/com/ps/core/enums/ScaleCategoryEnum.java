package com.ps.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScaleCategoryEnum {

    PERSONALITY(1, "人格测评"),
    MENTAL_HEALTH(2, "心理健康"),
    VOCATIONAL(3, "职业测评"),
    INTELLIGENCE(4, "智力测评"),
    FAMILY(5, "家庭测评"),
    EDUCATION(6, "教育测评"),
    CLINICAL(7, "临床测评"),
    OTHER(0, "其他");

    private final Integer code;
    private final String description;

    public static ScaleCategoryEnum of(Integer code) {
        if (code == null) {
            return null;
        }
        for (ScaleCategoryEnum category : values()) {
            if (category.code.equals(code)) {
                return category;
            }
        }
        return null;
    }
}
