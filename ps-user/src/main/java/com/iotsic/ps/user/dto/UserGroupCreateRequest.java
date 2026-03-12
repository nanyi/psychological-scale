package com.iotsic.ps.user.dto;

import lombok.Data;

/**
 * 用户组创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserGroupCreateRequest {

    /**
     * 用户组名称
     */
    private String name;

    /**
     * 用户组编码
     */
    private String code;

    /**
     * 描述
     */
    private String description;

    /**
     * 企业ID
     */
    private Long enterpriseId;
}
