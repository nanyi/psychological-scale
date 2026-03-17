package com.iotsic.ps.common.utils.json.jackson;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.iotsic.ps.common.utils.json.databind.TimestampLocalDateTimeDeserializer;
import com.iotsic.ps.common.utils.json.databind.TimestampLocalDateTimeSerializer;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间序列化反序列化规则
 *
 * @author Ryan
 * @date 2025-04-04 22:04
 */
public class SmartJavaTimeModule extends SimpleModule {

    public SmartJavaTimeModule() {
        super(PackageVersion.VERSION);

        // ======================= 时间序列化规则 ===============================
        // yyyy-MM-dd HH:mm:ss
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
        // yyyy-MM-dd
        addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        // HH:mm:ss
        addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        // Instant 类型序列化
        addSerializer(Instant.class, InstantSerializer.INSTANCE);
        // Duration 类型序列化
        addSerializer(Duration.class, DurationSerializer.INSTANCE);
        // Timestamp 类型序列化
        addSerializer(Timestamp.class, TimestampLocalDateTimeSerializer.INSTANCE);

        // ======================= 时间反序列化规则 ==============================
        // yyyy-MM-dd HH:mm:ss
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER));
        // yyyy-MM-dd
        addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        // HH:mm:ss
        addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        // Instant 反序列化
        addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        // Duration 反序列化
        addDeserializer(Duration.class, DurationDeserializer.INSTANCE);
        // Timestamp 反序列化
        addDeserializer(Timestamp.class, TimestampLocalDateTimeDeserializer.INSTANCE);
    }

}
