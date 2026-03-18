package com.iotsic.ps.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.Department;

import java.util.List;

/**
 * 部门服务接口
 * 
 * @author Ryan
 * @since 2026-03-18
 */
public interface DepartmentService {

    /**
     * 获取部门树形结构
     *
     * @return 部门树形列表
     */
    List<Department> getDepartmentTree();

    /**
     * 获取所有部门
     *
     * @return 部门列表
     */
    List<Department> getAllDepartments();

    /**
     * 获取部门分页列表
     *
     * @param request 分页请求
     * @param departmentName 部门名称（模糊搜索）
     * @param status 状态
     * @return 部门分页列表
     */
    PageResult<Department> getDepartmentPage(PageRequest request, String departmentName, Integer status);

    /**
     * 根据ID获取部门
     *
     * @param id 部门ID
     * @return 部门信息
     */
    Department getDepartmentById(Long id);

    /**
     * 创建部门
     *
     * @param department 部门信息
     * @return 创建的部门
     */
    Department createDepartment(Department department);

    /**
     * 更新部门
     *
     * @param id 部门ID
     * @param department 部门信息
     * @return 更新后的部门
     */
    Department updateDepartment(Long id, Department department);

    /**
     * 删除部门
     *
     * @param id 部门ID
     */
    void deleteDepartment(Long id);

    /**
     * 根据企业ID获取部门列表
     *
     * @param enterpriseId 企业ID
     * @return 部门列表
     */
    List<Department> getDepartmentsByEnterprise(Long enterpriseId);
}
