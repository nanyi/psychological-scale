package com.iotsic.ps.user.mapper;

import com.iotsic.ps.core.entity.User;
import com.iotsic.smart.framework.mybatis.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 *
 * @author Ryan
 */
@Mapper
public interface UserMapper extends BaseMapperPlus<User> {
}
