package com.iotsic.smart.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志响应VO
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Data
@Schema(description = "登录日志响应")
public class LoginLogVO implements Serializable {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "日志类型：1-登录，2-注销，3-刷新Token，4-踢出")
    private Integer logType;

    @Schema(description = "日志类型描述")
    private String logTypeDesc;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "用户类型")
    private Integer userType;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "登录结果：1-成功，2-失败")
    private Integer result;

    @Schema(description = "登录结果描述")
    private String resultDesc;

    @Schema(description = "失败原因")
    private String failReason;

    @Schema(description = "用户IP")
    private String userIp;

    @Schema(description = "设备类型")
    private String deviceType;

    @Schema(description = "设备ID")
    private String deviceId;

    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    @Schema(description = "租户编号")
    private Long tenantId;
}
