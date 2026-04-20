package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 量表分类实体
 * 
 * @author Ryan
 * @since 2026-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ps_scale_category")
public class ScaleCategory extends BaseEntity {

    /**
     * 分类名称
     */
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 父分类ID（0=一级分类）
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 排序
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 子分类列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<ScaleCategory> children;
}
