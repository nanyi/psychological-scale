package com.iotsic.ps.user.mapper;

import com.iotsic.ps.core.entity.UserRole;
import com.iotsic.smart.framework.mybatis.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 用户角色Mapper接口
 *
 * @author Ryan
 */
@Mapper
public interface UserRoleMapper extends BaseMapperPlus<UserRole> {

    default List<UserRole> selectListByUserId(Long userId) {
        return selectList(UserRole::getUserId, userId);
    }
}
