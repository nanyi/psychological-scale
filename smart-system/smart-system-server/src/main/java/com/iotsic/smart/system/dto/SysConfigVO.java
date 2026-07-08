package com.iotsic.smart.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统配置响应 VO
 *
 * @author Ryan
 * @since 2026-07-08
 */
@Data
@Schema(description = "系统配置响应")
public class SysConfigVO implements Serializable {

    @Schema(description = "配置ID")
    private Long id;

    @Schema(description = "参数分组")
    private String category;

    @Schema(description = "参数类型：1-文本，2-数字，3-布尔，4-JSON")
    private Integer configType;

    @Schema(description = "参数名称")
    private String configName;

    @Schema(description = "参数键名")
    private String configKey;

    @Schema(description = "参数键值")
    private String configValue;

    @Schema(description = "是否可见")
    private Boolean visible;

    @Schema(description = "是否系统内置")
    private Boolean isSystem;

    @Schema(description = "备注说明")
    private String remark;
}
