package com.iotsic.ps.user.vo;

import lombok.Data;

/**
 * @author Ryan
 */
@Data
public class LoginUserVO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色名称
     */
    private String role;
}
