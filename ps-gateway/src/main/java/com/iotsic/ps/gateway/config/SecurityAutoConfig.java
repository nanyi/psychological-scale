package com.iotsic.ps.gateway.config;

import com.iotsic.ps.gateway.config.properties.SecurityProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义的 Spring Security 配置适配器实现
 *
 * @author Ryan
 * @date 2025-01-31 17:35
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "smart.security", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SecurityAutoConfig {

    @Resource
    private SecurityProperties properties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(properties.getPasswordEncoderLength());
    }
}
