package com.iotsic.ps.scale.dto;

import lombok.Data;

/**
 * 量表分类请求DTO
 * 
 * @author Ryan
 * @since 2026-03-17
 */
@Data
public class ScaleCategoryRequest {

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 父分类ID（0或不填为一级分类）
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;
}
