package com.iotsic.ps.common.utils.json.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.iotsic.ps.common.utils.json.databind.LongNumberSerializer;

/**
 * Long类型 序列化反序列化规则
 *
 * @author Ryan
 * @date 2025-04-04 22:04
 */
public class SmartJavaNumberModule extends SimpleModule {

    public SmartJavaNumberModule() {
        super(PackageVersion.VERSION);

        // 新增 Long 类型序列化规则，数值超过 2^53-1，在 JS 会出现精度丢失问题，因此 Long 自动序列化为字符串类型
        addSerializer(Long.class, LongNumberSerializer.INSTANCE);
        addSerializer(Long.TYPE, LongNumberSerializer.INSTANCE);
    }

}
