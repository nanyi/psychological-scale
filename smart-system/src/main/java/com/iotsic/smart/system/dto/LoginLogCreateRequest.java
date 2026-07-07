package com.iotsic.smart.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录日志创建请求
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Data
@Schema(description = "登录日志创建请求")
public class LoginLogCreateRequest {

    @Schema(description = "日志类型：1-登录，2-注销，3-刷新Token，4-踢出")
    private Integer logType;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "用户类型")
    private Integer userType;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "登录结果：1-成功，2-失败")
    private Integer result;

    @Schema(description = "失败原因")
    private String failReason;

    @Schema(description = "用户IP")
    private String userIp;

    @Schema(description = "浏览器UA")
    private String userAgent;

    @Schema(description = "设备类型")
    private String deviceType;

    @Schema(description = "设备唯一标识")
    private String deviceId;

    @Schema(description = "租户编号")
    private Long tenantId;
}
