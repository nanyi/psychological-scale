package com.iotsic.ps.user.dto;

import com.iotsic.ps.user.vo.LoginUserVO;
import lombok.Data;

/**
 * 用户登录响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class UserLoginResponse {

    /**
     * 用户信息
     */
    private LoginUserVO user;

    /**
     * Token
     */
    private String token;
}
