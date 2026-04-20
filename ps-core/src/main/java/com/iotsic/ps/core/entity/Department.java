package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 部门实体类
 * 
 * @author Ryan
 * @since 2026-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_department")
public class Department extends BaseEntity {

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 父部门ID（0=一级部门）
     */
    private Long parentId;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

    /**
     * 子部门（不映射到数据库）
     */
    @TableField(exist = false)
    private List<Department> children;
}
