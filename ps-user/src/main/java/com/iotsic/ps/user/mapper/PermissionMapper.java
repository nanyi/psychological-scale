package com.iotsic.ps.user.mapper;

import com.iotsic.ps.core.entity.Permission;
import com.iotsic.smart.framework.mybatis.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 权限Mapper接口
 *
 * @author Ryan
 */
@Mapper
public interface PermissionMapper extends BaseMapperPlus<Permission> {

    default List<Permission> selectListByCode(String permissionCode) {
        return selectList(Permission::getPermissionCode, permissionCode);
    }
}
