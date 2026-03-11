package com.iotsic.ps.user.dto;

import lombok.Data;

/**
 * 用户分组创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserGroupCreateRequest {

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 分组描述
     */
    private String description;
}
