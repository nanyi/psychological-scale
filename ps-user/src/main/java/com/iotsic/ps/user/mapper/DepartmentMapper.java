package com.iotsic.ps.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iotsic.ps.core.entity.Department;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门Mapper接口
 * 
 * @author Ryan
 * @since 2026-03-18
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
}
