package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_group")
public class UserGroup extends BaseEntity {

    private String groupName;

    private String groupCode;

    private String description;

    private Long enterpriseId;

    private Long parentId;

    private Integer sort;

    private Integer memberCount;
}
