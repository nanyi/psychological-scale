package com.iotsic.smart.gateway.config.jackson;

import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.iotsic.smart.framework.common.utils.json.jackson.SmartJavaNumberModule;
import com.iotsic.smart.framework.common.utils.json.jackson.SmartJavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Jackson 配置
 *
 * @author Ryan
 */
@Configuration
@Slf4j
public class SmartJacksonAutoConfiguration implements WebFluxConfigurer {

    /**
     * 用 Customizer 增量定制，不破坏 Boot 的其它 Jackson 自动配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> builder
                .featuresToDisable(
                        // 对于空的对象转json的时候不抛出错误
                        SerializationFeature.FAIL_ON_EMPTY_BEANS
                        // 禁用时间戳格式化
                        , SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
                        // 禁用遇到未知属性抛出异常
                        , DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                        // 值为null，不抛出异常
                        , DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES
                )
                .featuresToEnable(
                        // 抛出引发错误的 JSON 片段
                        StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature()
                )
                .modulesToInstall(new SmartJavaTimeModule(), new SmartJavaNumberModule())
                .locale(Locale.CHINA)
                .timeZone(TimeZone.getTimeZone("GMT+8"));
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        // 让 codec 用的 ObjectMapper 就是容器里那个（已被上面的 Customizer 加工过）
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .build();

        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
    }
}
