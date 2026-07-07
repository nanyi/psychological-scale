package com.iotsic.smart.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * OAuth2 Token 请求参数
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Data
@Schema(description = "OAuth2 Token 请求参数")
public class OAuth2TokenRequest {

    @Schema(description = "授权类型: authorization_code, password, refresh_token")
    private String grantType;

    @Schema(description = "授权码（authorization_code模式）")
    private String code;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "客户端密钥")
    private String clientSecret;

    @Schema(description = "回调地址")
    private String redirectUri;

    @Schema(description = "授权范围")
    private List<String> scope;

    @Schema(description = "用户名（password模式）")
    private String username;

    @Schema(description = "密码（password模式）")
    private String password;

    @Schema(description = "刷新令牌（refresh_token模式）")
    private String refreshToken;

    @Schema(description = "租户ID")
    private String tenantId;

    @Schema(description = "设备类型: web, app, miniprogram, pc")
    private String deviceType;

    @Schema(description = "设备唯一标识")
    private String deviceId;

    @Schema(description = "是否记住我")
    private Boolean rememberMe;
}
