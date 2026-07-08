package com.iotsic.smart.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iotsic.smart.system.entity.LoginStrategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录策略 Mapper
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Mapper
public interface LoginStrategyMapper extends BaseMapper<LoginStrategy> {
}
