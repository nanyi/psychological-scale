package com.iotsic.smart.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iotsic.smart.system.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志Mapper
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {
}
