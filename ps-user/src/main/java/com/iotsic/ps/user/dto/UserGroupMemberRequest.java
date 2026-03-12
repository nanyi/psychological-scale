package com.iotsic.ps.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户组成员操作请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserGroupMemberRequest {

    /**
     * 用户组ID
     */
    private Long groupId;

    /**
     * 用户ID列表
     */
    private List<Long> userIds;
}
