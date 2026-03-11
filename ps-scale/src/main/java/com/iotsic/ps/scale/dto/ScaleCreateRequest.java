package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 量表创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScaleCreateRequest {

    /**
     * 量表名称
     */
    private String scaleName;

    /**
     * 量表分类
     */
    private Integer category;

    /**
     * 量表描述
     */
    private String description;

    /**
     * 指导语
     */
    private String instruction;

    /**
     * 状态
     */
    private Integer status;
}
