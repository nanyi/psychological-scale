package com.iotsic.ps.user.dto;

import lombok.Data;

/**
 * 企业创建请求DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class EnterpriseCreateRequest {

    /**
     * 企业名称
     */
    private String name;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 企业地址
     */
    private String address;

    /**
     * 状态
     */
    private Integer status;
}
