package com.iotsic.ps.user.mapper;

import com.iotsic.ps.core.entity.RolePermission;
import com.iotsic.smart.framework.mybatis.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 角色权限Mapper接口
 *
 * @author Ryan
 */
@Mapper
public interface RolePermissionMapper extends BaseMapperPlus<RolePermission> {

    default List<RolePermission> selectListByPermissionId(Long permissionId) {
        return selectList(RolePermission::getPermissionId, permissionId);
    }
}
