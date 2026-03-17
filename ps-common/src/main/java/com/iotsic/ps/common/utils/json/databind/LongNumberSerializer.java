package com.iotsic.ps.common.utils.json.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;

import java.io.IOException;

/**
 * Long 序列化规则
 * 会将超长 long 值转换为 string，解决前端 JavaScript 精度丢失的问题
 *
 * @author Ryan
 * @date 2025-03-31 0:49
 */
@JacksonStdImpl
public class LongNumberSerializer extends NumberSerializer {

    private static final long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final long MIN_SAFE_INTEGER = -9007199254740991L;

    public static final LongNumberSerializer INSTANCE = new LongNumberSerializer(Number.class);

    public LongNumberSerializer(Class<? extends Number> rawType) {
        super(rawType);
    }

    @Override
    public void serialize(Number value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        // 超出范围 序列化位字符串
        if (value.longValue() > MIN_SAFE_INTEGER && value.longValue() < MAX_SAFE_INTEGER) {
            super.serialize(value, generator, serializers);
        } else {
            generator.writeString(value.toString());
        }
    }

}
