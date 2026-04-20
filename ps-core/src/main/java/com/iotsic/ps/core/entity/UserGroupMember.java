package com.iotsic.ps.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_group_member")
public class UserGroupMember extends BaseEntity {

    private Long groupId;

    private Long userId;

    private Integer role;
}
