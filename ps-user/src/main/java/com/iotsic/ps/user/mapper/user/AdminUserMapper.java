package com.iotsic.ps.user.mapper.user;

import com.iotsic.ps.core.entity.AdminUser;
import com.iotsic.smart.framework.mybatis.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理后台用户Mapper接口
 *
 * @author Ryan
 * @since 2026-04-28 13:39
 */
@Mapper
public interface AdminUserMapper extends BaseMapperPlus<AdminUser> {
}
