package com.iotsic.ps.gateway.config.properties;

import com.iotsic.smart.framework.common.factory.YmlPropertySourceFactory;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

/**
 * Security 配置属性
 *
 * @author Ryan
 */
@Data
@Validated
@PropertySource(value = "classpath:security.yml", factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * Token 存储在 Header 中的名称
     */
    @NotEmpty(message = "Token Header 不能为空")
    private String tokenHeader = "Authorization";

    /**
     * Token 前缀
     */
    private String tokenPrefix = "Bearer ";

    /**
     * Token 参数名称
     * <p>HTTP 请求时，访问令牌的请求参数。解决 WebSocket 无法通过 header 传参，只能通过 token 参数拼接</p>
     */
    @NotEmpty(message = "Token Parameter 不能为空")
    private String tokenParameter = "token";

    /**
     * 密钥
     */
    private String secret = "iotsic-smart-security-secret-key-2026";

    /**
     * Token 过期时间
     */
    private Long expire = 7200000L;

    /**
     * Refresh Token 过期时间
     */
    private Long refreshExpire = 604800000L;

    /**
     * 缓存前缀
     */
    private String cachePrefix = "security:";

    /**
     * 最大登录设备数
     */
    private Integer maxLoginDevices = 5;

    /**
     * 签发者
     */
    private String issuer = "iotsic-smart-security";

    /**
     * PasswordEncoder 加密复杂度，越高开销越大
     */
    private Integer passwordEncoderLength = 4;

    /**
     * 免登录的 URL 列表
     */
    private String[] excludes;

    public String[] getExcludes() {
        return excludes == null ? excludes = new String[0] : excludes;
    }
}
