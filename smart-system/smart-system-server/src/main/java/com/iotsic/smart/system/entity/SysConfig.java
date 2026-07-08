package com.iotsic.smart.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置实体
 *
 * @author Ryan
 * @since 2026-07-08
 */
@Data
@TableName("sys_config")
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 参数分组：basic/security/notification/email/other
     */
    private String category;

    /**
     * 参数类型：1-文本，2-数字，3-布尔，4-JSON
     */
    private Integer configType;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 是否可见
     */
    private Boolean visible;

    /**
     * 是否系统内置
     */
    private Boolean isSystem;

    /**
     * 备注说明
     */
    private String remark;
}
