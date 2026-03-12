package com.iotsic.ps.user.dto;

import lombok.Data;

/**
 * 用户组成员响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class GroupMembersResponse {

    /**
     * 用户组ID
     */
    private Long groupId;

    /**
     * 用户组名称
     */
    private String groupName;

    /**
     * 成员数量
     */
    private Integer memberCount;

    /**
     * 成员ID列表
     */
    private java.util.List<Long> memberIds;
}
