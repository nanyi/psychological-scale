package com.iotsic.ps.common.utils;

import com.iotsic.ps.common.response.PageResult;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Bean 工具类
 * 1. 默认使用 {@link org.springframework.beans.BeanUtils} 作为实现类
 * 2. 针对复杂的对象转换，可以搜参考 AuthConvert 实现，通过 mapstruct + default 配合实现
 *
 * @author Ryan
 * @date 2025-03-18 17:58:29
 **/
public class BeanUtils {

    /**
     * 将源对象转换为目标类的实例，使用 Spring BeanUtils 进行属性拷贝
     *
     * @param source 源对象，可以是任意 Java 对象
     * @param targetClass 目标类的 Class 对象
     * @param <T> 目标类的泛型类型
     * @return 转换后的目标对象实例
     */
    public static <T> T toBean(Object source, Class<T> targetClass) {
        T t = org.springframework.beans.BeanUtils.instantiateClass(targetClass);
        org.springframework.beans.BeanUtils.copyProperties(source, t);
        return t;
    }

    /**
     * 将源对象转换为目标类的实例，并在转换后执行自定义处理逻辑
     *
     * @param source 源对象，可以是任意 Java 对象
     * @param targetClass 目标类的 Class 对象
     * @param peek 处理函数，用于在对象创建后进行额外操作（如设置默认值、校验等）
     * @param <T> 目标类的泛型类型
     * @return 转换后的目标对象实例
     */
    public static <T> T toBean(Object source, Class<T> targetClass, Consumer<T> peek) {
        T target = toBean(source, targetClass);
        peek.accept(target);
        return target;
    }

    /**
     * 将源列表中的每个元素转换为目标类的实例，返回新的列表
     *
     * @param source 源列表，可以为 null
     * @param targetType 目标类的 Class 对象
     * @param <S> 源对象的泛型类型
     * @param <T> 目标类的泛型类型
     * @return 转换后的目标对象列表，如果源列表为 null 则返回空列表
     */
    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType) {
        if (source == null) {
            return List.of();
        }
        return source.stream().filter(Objects::nonNull).map(s -> toBean(s, targetType)).collect(Collectors.toList());
    }

    /**
     * 将源列表中的每个元素转换为目标类的实例，并在转换后执行自定义处理逻辑
     *
     * @param source 源列表，可以为 null
     * @param targetType 目标类的 Class 对象
     * @param peek 处理函数，用于在每个对象创建后进行额外操作
     * @param <S> 源对象的泛型类型
     * @param <T> 目标类的泛型类型
     * @return 转换后的目标对象列表，如果源列表为 null 则返回空列表
     */
    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType, Consumer<T> peek) {
        List<T> list = toBean(source, targetType);
        if (list != null) {
            list.forEach(peek);
        }
        return list;
    }

    /**
     * 将分页结果中的数据列表转换为目标类的实例，保持分页信息不变
     *
     * @param source 源分页结果对象
     * @param targetType 目标类的 Class 对象
     * @param <S> 源对象的泛型类型
     * @param <T> 目标类的泛型类型
     * @return 转换后的分页结果对象
     */
    public static <S, T> PageResult<T> toBean(PageResult<S> source, Class<T> targetType) {
        return toBean(source, targetType, null);
    }

    /**
     * 将分页结果中的数据列表转换为目标类的实例，并在转换后执行自定义处理逻辑
     *
     * @param source 源分页结果对象
     * @param targetType 目标类的 Class 对象
     * @param peek 处理函数，用于在每个对象创建后进行额外操作
     * @param <S> 源对象的泛型类型
     * @param <T> 目标类的泛型类型
     * @return 转换后的分页结果对象
     */
    public static <S, T> PageResult<T> toBean(PageResult<S> source, Class<T> targetType, Consumer<T> peek) {
        if (source == null) {
            return PageResult.empty();
        }
        List<T> records = toBean(source.getRecords(), targetType);
        if (peek != null) {
            records.forEach(peek);
        }
        return new PageResult<>(records, source.getTotal());
    }

}
