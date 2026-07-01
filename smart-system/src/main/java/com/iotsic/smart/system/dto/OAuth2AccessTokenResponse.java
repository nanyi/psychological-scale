package com.iotsic.smart.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "租户编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long tenantId;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "用户类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userType;

    @Schema(description = "用户信息")
    private Map<String, String> userInfo;

    @Schema(description = "授权范围的数组", example = "user_info")
    private List<String> scopes;

    @Schema(description = "过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime expiresTime;
}
