package com.iotsic.ps.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举工具类
 *
 * @author Ryan
 * @date 2026-01-27 17:26
 */
public class EnumUtils {

    private static final Map<Class<?>, Enum<?>[]> CACHE = new ConcurrentHashMap<>();

    /**
     * 枚举类中所有枚举对象的name列表
     *
     * @param clazz 枚举类
     * @return name列表
     */
    public static List<String> getNames(Class<? extends Enum<?>> clazz) {
        if (null == clazz) {
            return null;
        }
        final Enum<?>[] enums = getEnums2(clazz);
        if (null == enums) {
            return null;
        }
        final List<String> list = new ArrayList<>(enums.length);
        for (Enum<?> e : enums) {
            list.add(e.name());
        }
        return list;
    }

    /**
     * 获取枚举类中的枚举值，调用过的枚举值会缓存，下次调用会从缓存中获取
     *
     * @param enumClass 枚举类
     * @return 枚举类中的枚举值
     */
    private static Enum<?>[] getEnums2(final Class<? extends Enum<?>> enumClass) {
        if (null == enumClass) {
            return null;
        }
        return CACHE.computeIfAbsent(enumClass, (k) -> enumClass.getEnumConstants());
    }


    /**
     * 字符串转枚举，调用{@link Enum#valueOf(Class, String)}
     *
     * @param <E>       枚举类型泛型
     * @param enumClass 枚举类
     * @param value     值
     * @return 枚举值
     * @since 4.1.13
     */
    public static <E extends Enum<E>> E fromString(Class<E> enumClass, String value) {
        if (null == enumClass || StrUtil.isBlank(value)) {
            return null;
        }
        return Enum.valueOf(enumClass, value);
    }

}
