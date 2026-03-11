package com.ps.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ps.core.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
