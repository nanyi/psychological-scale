package com.iotsic.smart.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志查询请求
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Data
@Schema(description = "登录日志查询请求")
public class LoginLogRequest {

    @Schema(description = "用户名（模糊查询）")
    private String username;

    @Schema(description = "日志类型")
    private Integer logType;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;
}
