package com.iotsic.ps.common.utils.json.databind;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 基于时间戳的 String LocalDateTime 序列化器
 *
 * @author Ryan
 * @date 2025-03-31 0:52
 */
public class TimestampLocalDateTimeSerializer extends JsonSerializer<Timestamp> {
    public static final TimestampLocalDateTimeSerializer INSTANCE = new TimestampLocalDateTimeSerializer();

    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 将 Timestamp 对象，转换为 String LocalDateTime 时间
        SimpleDateFormat sdf = new SimpleDateFormat(DatePattern.NORM_DATETIME_MS_PATTERN);
        gen.writeString(sdf.format(value));
    }

}
