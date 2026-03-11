package com.iotsic.ps.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iotsic.ps.core.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
