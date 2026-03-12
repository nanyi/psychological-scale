package com.iotsic.ps.user.service;

import com.iotsic.ps.core.entity.User;
import com.iotsic.ps.user.dto.AuthResultDTO;
import com.iotsic.ps.user.vo.UserVO;

/**
 * 用户服务接口
 * 负责用户的注册、登录、登出、Token刷新等业务操作
 *
 * @author Ryan
 * @since 2026-03-12
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @param phone 手机号
     * @param email 邮箱
     * @return 认证结果
     */
    AuthResultDTO register(String username, String password, String phone, String email);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @param loginIp 登录IP
     * @return 认证结果
     */
    AuthResultDTO login(String username, String password, String loginIp);

    /**
     * 用户登出
     *
     * @param userId 用户ID
     */
    void logout(Long userId);

    /**
     * 刷新Token
     *
     * @param refreshToken 刷新令牌
     * @return 新的认证结果
     */
    AuthResultDTO refreshToken(String refreshToken);

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户实体
     */
    User getUserById(Long id);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    User getUserByUsername(String username);

    /**
     * 根据手机号获取用户
     *
     * @param phone 手机号
     * @return 用户实体
     */
    User getUserByPhone(String phone);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户视图对象
     */
    UserVO getUserInfo(Long userId);
}
