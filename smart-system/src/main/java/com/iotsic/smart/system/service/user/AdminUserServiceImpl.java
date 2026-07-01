package com.iotsic.smart.system.service.user;

import com.iotsic.ps.core.entity.AdminUser;
import com.iotsic.smart.system.mapper.user.AdminUserMapper;
import com.iotsic.smart.system.service.AdminUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 管理端用户服务实现类
 *
 * @author Ryan
 * @since 2026-04-28 13:37
 */
@Slf4j
@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser getUser(Long userId) {
        return adminUserMapper.selectById(userId);
    }
}
