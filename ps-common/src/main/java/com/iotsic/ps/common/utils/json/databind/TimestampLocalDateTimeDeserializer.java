package com.iotsic.ps.common.utils.json.databind;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.iotsic.ps.common.utils.StringUtils;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * 基于时间戳的 String LocalDateTime 反序列化器
 *
 * @author Ryan
 * @date 2025-03-31 0:52
 */
public class TimestampLocalDateTimeDeserializer extends JsonDeserializer<Timestamp> {

    public static final TimestampLocalDateTimeDeserializer INSTANCE = new TimestampLocalDateTimeDeserializer();

    @Override
    public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // 将 String LocalDateTime 时间，转换为  Timestamp 对象
        return StringUtils.isEmpty(p.getText()) ? null : new Timestamp(new DateTime(p.getText()).getTime());
    }

}
