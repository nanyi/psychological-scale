package com.iotsic.ps.user.dto;

import lombok.Data;

/**
 * 用户组更新请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserGroupUpdateRequest {

    /**
     * 用户组名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;
}
