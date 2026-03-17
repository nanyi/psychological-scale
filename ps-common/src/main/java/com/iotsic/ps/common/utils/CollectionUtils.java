package com.iotsic.ps.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Collection 工具类
 *
 * @author Ryan
 * @date 2025-03-29 16:44
 */
public class CollectionUtils {

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 检查源对象是否包含在目标对象数组中，用于确定给定的值数组中是否存在特定值。
     *
     * @param source 要检查的对象可以是任何类型
     * @param targets 要搜索的对象数组可以包含零个或多个元素
     * @return 如果在目标数组中找到源对象，则返回 true;否则，返回 false
     */
    public static boolean containsAny(Object source, Object... targets) {
        return asList(targets).contains(source);
    }

    /**
     * 使用Predicate过滤集合中的元素
     * 此方法提供了一个通用的过滤机制，用于从任何Collection类型中过滤出满足指定条件的元素
     * 它利用了Java 8的Stream API和Predicate接口，使得过滤逻辑更加灵活和可重用
     *
     * @param from 需要被过滤的原始集合，类型为Collection<T>
     * @param predicate 过滤条件，是一个Predicate<T>类型的函数式接口实例
     * @param <T> 泛型参数，表示集合中元素的类型
     * @return 泛型参数，表示集合中元素的类型
     */
    public static <T> List<T> filterList(Collection<T> from, Predicate<T> predicate) {
        if (isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 根据指定的键映射函数和合并策略，从一个集合中提取唯一元素列表
     * 用于处理集合中的重复元素，通过键映射函数确定元素的唯一性，并使用合并策略处理具有相同键的元素
     *
     * @param <T> 元素类型
     * @param <R> 键类型
     * @param from 源集合
     * @param keyMapper 键映射函数，用于提取元素的唯一键
     * @return 唯一元素列表
     */
    public static <T, R> List<T> distinct(Collection<T> from, Function<T, R> keyMapper) {
        if (isEmpty(from)) {
            return new ArrayList<>();
        }
        return distinct(from, keyMapper, (t1, t2) -> t1);
    }

    /**
     * 根据指定的键映射函数和合并策略，从一个集合中提取唯一元素列表
     * 用于处理集合中的重复元素，通过键映射函数确定元素的唯一性，并使用合并策略处理具有相同键的元素
     *
     * @param <T> 元素类型
     * @param <R> 键类型
     * @param from 源集合
     * @param keyMapper 键映射函数，用于提取元素的唯一键
     * @param cover 合并策略，用于处理具有相同键的元素
     * @return 唯一元素列表
     */
    public static <T, R> List<T> distinct(Collection<T> from, Function<T, R> keyMapper, BinaryOperator<T> cover) {
        if (isEmpty(from)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(convertMap(from, keyMapper, Function.identity(), cover, LinkedHashMap::new).values());
    }

    /**
     * 将给定集合中的元素转换为一个自定义映射
     * 此方法允许用户提供自定义的键值提取函数以及映射合并函数，还可以选择性地提供一个映射的供应商
     *
     * @param <T> 输入集合中元素的类型
     * @param <K> 生成的映射中键的类型
     * @param from 输入的集合，其元素将被转换为映射
     * @param keyFunc 用于从输入集合的每个元素中提取映射键的函数
     * @return 转换后的映射
     */
    public static <T, K> Map<K, T> convertMap(Collection<T> from, Function<T, K> keyFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, Function.identity());
    }

    /**
     * 将给定集合中的元素转换为一个自定义映射
     * 此方法允许用户提供自定义的键值提取函数以及映射合并函数，还可以选择性地提供一个映射的供应商
     *
     * @param <T> 输入集合中元素的类型
     * @param <K> 生成的映射中键的类型
     * @param <V> 生成的映射中值的类型
     * @param from 输入的集合，其元素将被转换为映射
     * @param keyFunc 用于从输入集合的每个元素中提取映射键的函数
     * @param valueFunc 用于从输入集合的每个元素中提取映射值的函数
     * @return 转换后的映射
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1);
    }

    /**
     * 将给定集合中的元素转换为一个自定义映射
     * 此方法允许用户提供自定义的键值提取函数以及映射合并函数，还可以选择性地提供一个映射的供应商
     *
     * @param <T> 输入集合中元素的类型
     * @param <K> 生成的映射中键的类型
     * @param <V> 生成的映射中值的类型
     * @param from 输入的集合，其元素将被转换为映射
     * @param keyFunc 用于从输入集合的每个元素中提取映射键的函数
     * @param valueFunc 用于从输入集合的每个元素中提取映射值的函数
     * @param mergeFunction 当有重复键时，用于合并映射值的函数
     * @return 转换后的映射
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
        if (isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, valueFunc, mergeFunction, HashMap::new);
    }

    /**
     * 将给定集合中的元素转换为一个自定义映射
     * 此方法允许用户提供自定义的键值提取函数以及映射合并函数，还可以选择性地提供一个映射的供应商
     *
     * @param <T> 输入集合中元素的类型
     * @param <K> 生成的映射中键的类型
     * @param <V> 生成的映射中值的类型
     * @param from 输入的集合，其元素将被转换为映射
     * @param keyFunc 用于从输入集合的每个元素中提取映射键的函数
     * @param valueFunc 用于从输入集合的每个元素中提取映射值的函数
     * @param supplier 用于创建映射实例的供应商
     * @return 转换后的映射
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, Supplier<? extends Map<K, V>> supplier) {
        if (isEmpty(from)) {
            return supplier.get();
        }
        return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
    }

    /**
     * 将给定集合中的元素转换为一个自定义映射
     * 此方法允许用户提供自定义的键值提取函数以及映射合并函数，还可以选择性地提供一个映射的供应商
     *
     * @param <T> 输入集合中元素的类型
     * @param <K> 生成的映射中键的类型
     * @param <V> 生成的映射中值的类型
     * @param from 输入的集合，其元素将被转换为映射
     * @param keyFunc 用于从输入集合的每个元素中提取映射键的函数
     * @param valueFunc 用于从输入集合的每个元素中提取映射值的函数
     * @param mergeFunction 当有重复键时，用于合并映射值的函数
     * @param supplier 用于创建映射实例的供应商
     * @return 转换后的映射
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> supplier) {
        if (isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().collect(Collectors.toMap(keyFunc, valueFunc, mergeFunction, supplier));
    }

    /**
     * 对比老、新两个列表，找出新增、修改、删除的数据
     *
     * @param oldList  老列表
     * @param newList  新列表
     * @param sameFunc 对比函数，返回 true 表示相同，返回 false 表示不同
     *                 注意，same 是通过每个元素的“标识”，判断它们是不是同一个数据
     * @return [新增列表、修改列表、删除列表]
     */
    public static <T> List<List<T>> diffList(Collection<T> oldList, Collection<T> newList, BiFunction<T, T, Boolean> sameFunc) {
        List<T> createList = new LinkedList<>(newList);
        List<T> updateList = new ArrayList<>();
        List<T> deleteList = new ArrayList<>();

        for (T oldObj : oldList) {
            T foundObj = null;
            for (Iterator<T> iterator = createList.iterator(); iterator.hasNext(); ) {
                T newObj = iterator.next();
                if (!sameFunc.apply(oldObj, newObj)) {
                    continue;
                }
                iterator.remove();
                foundObj = newObj;
                break;
            }
            if (foundObj != null) {
                updateList.add(foundObj);
            } else {
                deleteList.add(oldObj);
            }
        }
        return asList(createList, updateList, deleteList);
    }

    /**
     * 将数组中的每个元素通过函数转换为新的类型，并返回转换后的列表
     *
     * @param from 源数组，可以是任意类型的数组
     * @param func 转换函数，用于将源元素映射为新类型
     * @param <T> 源元素的泛型类型
     * @param <U> 目标元素的泛型类型
     * @return 转换后的列表，如果源数组为空则返回空列表
     */
    public static <T, U> List<U> convertList(T[] from, Function<T, U> func) {
        if (ArrayUtil.isEmpty(from)) {
            return new ArrayList<>();
        }
        return convertList(Arrays.asList(from), func);
    }

    /**
     * 将集合中的每个元素通过函数转换为新的类型，并返回转换后的列表
     *
     * @param from 源集合，可以是任意 Collection 类型
     * @param func 转换函数，用于将源元素映射为新类型
     * @param <T> 源元素的泛型类型
     * @param <U> 目标元素的泛型类型
     * @return 转换后的列表，如果源集合为空则返回空列表
     */
    public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func) {
        if (CollUtil.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 将集合中的每个元素先进行过滤，再通过函数转换为新的类型，并返回转换后的列表
     *
     * @param from 源集合，可以是任意 Collection 类型
     * @param func 转换函数，用于将源元素映射为新类型
     * @param filter 过滤条件，仅处理满足条件的元素
     * @param <T> 源元素的泛型类型
     * @param <U> 目标元素的泛型类型
     * @return 转换后的列表，如果源集合为空或无满足条件的元素则返回空列表
     */
    public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func, Predicate<T> filter) {
        if (CollUtil.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().filter(filter).map(func).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 将集合中的每个元素通过函数转换为新的类型，并返回转换后的集合
     * @param from 源集合，可以是任意 Collection 类型
     * @return 转换后的集合，如果源集合为空则返回空集合
     * @param <T> 源元素的泛型类型
     */
    public static <T> Set<T> convertSet(Collection<T> from) {
        return convertSet(from, v -> v);
    }

    /**
     * 将集合中的每个元素通过函数转换为新的类型，并返回转换后的集合
     * @param from 源集合，可以是任意 Collection 类型
     * @param func 转换函数，用于将源元素映射为新类型
     * @return 转换后的集合，如果源集合为空则返回空集合
     * @param <T> 源元素的泛型类型
     * @param <U> 目标元素的泛型类型
     */
    public static <T, U> Set<U> convertSet(Collection<T> from, Function<T, U> func) {
        if (CollUtil.isEmpty(from)) {
            return new HashSet<>();
        }
        return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * 将集合中的每个元素先进行过滤，再通过函数转换为新的类型，并返回转换后的集合
     * @param from 源集合，可以是任意 Collection 类型
     * @param func 转换函数，用于将源元素映射为新类型
     * @param filter 过滤条件，仅处理满足条件的元素
     * @return 转换后的集合，如果源集合为空或无满足条件的
     * @param <T> 源元素的泛型类型
     * @param <U> 目标元素的泛型类型
     */
    public static <T, U> Set<U> convertSet(Collection<T> from, Function<T, U> func, Predicate<T> filter) {
        if (CollUtil.isEmpty(from)) {
            return new HashSet<>();
        }
        return from.stream().filter(filter).map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * 创建一个包含单个元素的集合
     * @param obj 单个元素
     * @return 包含单个元素的集合
     * @param <T> 元素的类型
     */
    public static <T> Collection<T> singleton(T obj) {
        return obj == null ? Collections.emptyList() : Collections.singleton(obj);
    }

    /**
     * 加入全部
     *
     * @param <T>        集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param iterator   要加入的{@link Iterator}
     * @return 原集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Iterator<T> iterator) {
        if (null != collection && null != iterator) {
            while (iterator.hasNext()) {
                collection.add(iterator.next());
            }
        }
        return collection;
    }

    /**
     * 加入全部
     *
     * @param <T>        集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param iterable   要加入的内容{@link Iterable}
     * @return 原集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Iterable<T> iterable) {
        if (iterable == null) {
            return collection;
        }
        return addAll(collection, iterable.iterator());
    }

}
