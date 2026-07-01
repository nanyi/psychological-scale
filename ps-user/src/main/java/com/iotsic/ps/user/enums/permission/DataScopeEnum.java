package com.iotsic.ps.user.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据范围枚举类
 *
 * @author Ryan
 * @since 2026-04-28 22:57
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {

    ALL(1, "全部数据权限"),
    DEPT_CUSTOM(2, "指定部门数据权限"),
    DEPT_ONLY(3, "部门数据权限"),
    DEPT_AND_CHILD(4, "部门及以下数据权限"),

    SELF(5, "仅本人数据权限");

    /**
     * 范围
     */
    private final Integer scope;

    /**
     * 描述
     */
    private final String description;
}
