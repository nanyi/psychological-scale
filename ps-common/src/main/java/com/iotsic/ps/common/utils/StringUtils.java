package com.iotsic.ps.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * String 工具类
 *
 * @author Ryan
 * @date 2025-03-28 10:36
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String SEPARATOR = ",";

    /**
     * 去除空格
     */
    public static String trim(String str) {
        return (str == null ? StringUtils.EMPTY : str.trim());
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return StringUtils.EMPTY;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return StringUtils.EMPTY;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return StringUtils.EMPTY;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (start > end) {
            return StringUtils.EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is {} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        return StrUtil.format(template, params);
    }

    /**
     * 字符串转list
     *
     * @param str         字符串
     * @param sep         分隔符
     * @param filterBlank 过滤纯空白
     * @param trim        去掉首尾空白
     * @return list集合
     */
    public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str)) {
            return list;
        }

        // 过滤空白字符串
        if (filterBlank && StringUtils.isBlank(str)) {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split) {
            if (filterBlank && StringUtils.isBlank(string)) {
                continue;
            }
            if (trim) {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * 字符串转set
     *
     * @param str 字符串
     * @param sep 分隔符
     * @return set集合
     */
    public static final Set<String> str2Set(String str, String sep) {
        return new HashSet<String>(str2List(str, sep, true, false));
    }

    /**
     * 切分字符串，去除切分后每个元素两边的空白符，去除空白项
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     */
    public static List<String> splitTrim(String str, char separator) {
        return splitTrim(str, separator, -1);
    }

    /**
     * 切分字符串，去除切分后每个元素两边的空白符，去除空白项
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数，-1不限制
     * @return 切分后的集合
     */
    public static List<String> splitTrim(String str, char separator, int limit) {
        return split(str, separator, limit, true, true);
    }

    /**
     * 切分字符串
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, (s -> isTrim ? trim(s) : s));
    }

    /**
     * 切分字符串<br>
     * 如果为空字符串或者null 则返回空集合
     *
     * @param <R>         切分后的元素类型
     * @param text        被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param ignoreEmpty 是否忽略空串
     * @param ignoreCase  是否忽略大小写
     * @param mapping     切分后的字符串元素的转换方法
     * @return 切分后的集合，元素类型是经过 mapping 转换后的
     */
    public static <R> List<R> split(String text, char separator, int limit, boolean ignoreEmpty,
                                    boolean ignoreCase, Function<String, R> mapping) {
        if (null == text) {
            return new ArrayList<>(0);
        }
        return toList(text, separator, limit, ignoreEmpty, ignoreCase, mapping);
    }

    /**
     * 切分字符串自定义转换(分隔符默认逗号)
     *
     * @param str    被切分的字符串
     * @param mapper 自定义转换
     * @return 分割后的数据列表
     */
    public static <T> List<T> splitTo(String str, Function<? super Object, T> mapper) {
        return splitTo(str, SEPARATOR, mapper);
    }

    /**
     * 切分字符串自定义转换
     *
     * @param str       被切分的字符串
     * @param separator 分隔符
     * @param mapper    自定义转换
     * @return 分割后的数据列表
     */
    public static <T> List<T> splitTo(String str, String separator, Function<? super Object, T> mapper) {
        if (isBlank(str)) {
            return new ArrayList<>(0);
        }
        return StrUtil.split(str, separator)
                .stream()
                .filter(Objects::nonNull)
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 获取切分后的对象列表
     *
     * @param <R>         切分后的元素类型
     * @param text        被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param ignoreEmpty 是否忽略空串
     * @param ignoreCase  是否忽略大小写
     * @param mapping     切分后的字符串元素的转换方法
     * @return 切分后的列表，元素类型是经过 mapping 转换后的
     */
    public static <R> List<R> toList(String text, char separator, int limit, boolean ignoreEmpty,
                                     boolean ignoreCase, Function<String, R> mapping) {
        final List<R> result = new ArrayList<>();

        limit = limit > 0 ? limit : text.length();

        // 上一次的结束位置
        int offset = 0;
        // 计数器，用于判断是否超过limit
        int count = 0;

        // 数量未达上限且未达末尾，继续切分
        while (count < limit && offset <= text.length()) {
            String str = null;
            // 达到数量上限
            if (count == (limit - 1)) {
                // 当到达限制次数时，最后一个元素为剩余部分
                if (ignoreEmpty && offset == text.length()) {
                    // 最后一个是空串
                    str = null;
                } else {
                    // 结尾整个作为一个元素
                    str = text.substring(offset);
                }
            } else {
                int start = -1;
                for (int i = offset; i < text.length(); i++) {
                    if (equals(separator, text.charAt(i), ignoreCase)) {
                        start = i;
                        break;
                    }
                }

                // 无分隔符，结束
                if (start < 0) {
                    // 如果不再有分隔符，但是遗留了字符，则单独作为一个段
                    str = text.substring(offset);
                    if (!ignoreEmpty || !str.isEmpty()) {
                        // 设为末尾，跳出下一次循环
                        offset = Integer.MAX_VALUE;
                    }
                } else {
                    // 找到新的分隔符位置
                    str = text.substring(offset, start);

                    offset = start + 1;

                    if (ignoreEmpty && str.isEmpty()) {
                        // 发现空串且需要忽略时，跳过之
                        continue;
                    }
                }
            }

            count++;

            final R apply = mapping.apply(str);
            if (ignoreEmpty && StringUtils.isEmptyIfStr(apply)) {
                // 对于mapping之后依旧是String的情况，ignoreEmpty依旧有效
                continue;
            }
            result.add(apply);
        }
        if (result.isEmpty()) {
            return new ArrayList<>(0);
        }
        return result;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || str.trim().isEmpty();
    }

    /**
     * <p>如果对象是字符串是否为空白，空白的定义如下：</p>
     * <ol>
     *     <li>{@code null}</li>
     *     <li>空字符串：{@code ""}</li>
     *     <li>空格、全角空格、制表符、换行符，等不可见字符</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtils.isBlankIfStr(null)     // true}</li>
     *     <li>{@code StringUtils.isBlankIfStr("")       // true}</li>
     *     <li>{@code StringUtils.isBlankIfStr(" \t\n")  // true}</li>
     *     <li>{@code StringUtils.isBlankIfStr("abc")    // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isEmptyIfStr(Object)} 的区别是：
     * 该方法会校验空白字符，且性能相对于 {@link #isEmptyIfStr(Object)} 略慢。</p>
     *
     * @param obj 对象
     * @return 如果为字符串是否为空串
     * @see StringUtils#isBlank(CharSequence)
     */
    public static boolean isBlankIfStr(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            return isBlank((CharSequence) obj);
        }
        return false;
    }

    /**
     * <p>如果对象是字符串是否为空串，空的定义如下：</p><br>
     * <ol>
     *     <li>{@code null}</li>
     *     <li>空字符串：{@code ""}</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtils.isEmptyIfStr(null)     // true}</li>
     *     <li>{@code StringUtils.isEmptyIfStr("")       // true}</li>
     *     <li>{@code StringUtils.isEmptyIfStr(" \t\n")  // false}</li>
     *     <li>{@code StringUtils.isEmptyIfStr("abc")    // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link StringUtils#isBlankIfStr(Object)} 的区别是：该方法不校验空白字符。</p>
     *
     * @param obj 对象
     * @return 如果为字符串是否为空串
     */
    public static boolean isEmptyIfStr(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).isEmpty();
        }
        return false;
    }

    /**
     * 判断给定的集合列表中是否包含数组array 判断给定的数组array中是否包含给定的元素value
     *
     * @param collection   给定的集合
     * @param array 给定的数组
     * @return boolean 结果
     */
    public static boolean containsAny(Collection<String> collection, String... array) {
        if (isEmpty(collection) || isEmpty(array)) {
            return false;
        }
        for (String str : array) {
            if (collection.contains(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找指定字符串是否包含指定字符串列表中的任意一个字符串同时串忽略大小写
     *
     * @param cs                  指定字符串
     * @param searchCharSequences 需要检查的字符串数组
     * @return 是否包含任意一个字符串
     */
    public static boolean containsAnyIgnoreCase(CharSequence cs, CharSequence... searchCharSequences) {
        if (isEmpty(cs) || isEmpty(searchCharSequences)) {
            return false;
        }
        for (CharSequence testStr : searchCharSequences) {
            if (containsIgnoreCase(cs, testStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 不区分大小写检查 CharSequence 是否以指定的前缀开头。
     *
     * @param str     要检查的 CharSequence 可能为 null
     * @param prefixs 要查找的前缀可能为 null
     * @return 是否包含
     */
    public static boolean startWithAnyIgnoreCase(CharSequence str, CharSequence... prefixs) {
        // 判断是否是以指定字符串开头
        for (CharSequence prefix : prefixs) {
            if (StringUtils.startsWithIgnoreCase(str, prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 比较两个字符是否相同
     *
     * @param c1              字符1
     * @param c2              字符2
     * @param caseInsensitive 是否忽略大小写
     * @return 是否相同
     */
    public static boolean equals(char c1, char c2, boolean caseInsensitive) {
        if (caseInsensitive) {
            return Character.toLowerCase(c1) == Character.toLowerCase(c2);
        }
        return c1 == c2;
    }


    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        return StrUtil.upperFirst(StrUtil.toCamelCase(name));
    }

    /**
     * 驼峰式命名法 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        return StrUtil.toCamelCase(s);
    }

    /**
     * 获取参数不为空值
     *
     * @param str defaultValue 要判断的value
     * @return value 返回值
     */
    public static String blankToDefault(String str, String defaultValue) {
        return StrUtil.blankToDefault(str, defaultValue);
    }

}
