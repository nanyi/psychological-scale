package com.iotsic.smart.gateway.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * OAuth2访问令牌响应数据
 *
 * @author Ryan
 */
@Data
public class OAuth2AccessTokenResponse implements Serializable {

    private String tenantId;

    private Long userId;

    private Integer userType;

    private Map<String, String> userInfo;

    private List<String> scopes;

    private LocalDateTime expiresTime;
}
