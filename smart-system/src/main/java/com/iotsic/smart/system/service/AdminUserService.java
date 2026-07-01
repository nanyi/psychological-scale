package com.iotsic.smart.system.service;

import com.iotsic.ps.core.entity.AdminUser;

/**
 * 管理后台用户 Service 接口
 *
 * @author Ryan
 * @since 2026-04-28 13:34
 */
public interface AdminUserService {

    /**
     * 通过用户 ID 查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    AdminUser getUser(Long userId);
}
