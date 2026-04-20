package com.iotsic.ps.user.controller;

import com.iotsic.ps.core.entity.Department;
import com.iotsic.ps.user.service.DepartmentService;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门控制器
 * 负责部门的CRUD请求
 *
 * @author Ryan
 * @since 2026-03-18
 */
@Slf4j
@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * 获取部门树形结构
     *
     * @return 部门树形列表
     */
    @GetMapping("/tree")
    public RestResult<List<Department>> getDepartmentTree() {
        return RestResult.success(departmentService.getDepartmentTree());
    }

    /**
     * 获取所有部门
     *
     * @return 部门列表
     */
    @GetMapping("/all")
    public RestResult<List<Department>> getAllDepartments() {
        return RestResult.success(departmentService.getAllDepartments());
    }

    /**
     * 获取部门分页列表
     *
     * @param request 分页请求
     * @param departmentName 部门名称
     * @param status 状态
     * @return 部门分页列表
     */
    @GetMapping("/list")
    public RestResult<PageResult<Department>> getDepartmentList(
            PageRequest request,
            @RequestParam(value = "departmentName", required = false) String departmentName,
            @RequestParam(value = "status", required = false) Integer status) {
        return RestResult.success(departmentService.getDepartmentPage(request, departmentName, status));
    }

    /**
     * 根据ID获取部门
     *
     * @param id 部门ID
     * @return 部门信息
     */
    @GetMapping("/detail/{id}")
    public RestResult<Department> getDepartmentById(@PathVariable Long id) {
        return RestResult.success(departmentService.getDepartmentById(id));
    }

    /**
     * 创建部门
     *
     * @param department 部门信息
     * @return 创建的部门
     */
    @PostMapping("/create")
    public RestResult<Department> createDepartment(@RequestBody Department department) {
        Department result = departmentService.createDepartment(department);
        return RestResult.success("部门创建成功", result);
    }

    /**
     * 更新部门
     *
     * @param id 部门ID
     * @param department 部门信息
     * @return 操作结果
     */
    @PutMapping("/update/{id}")
    public RestResult<Void> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        departmentService.updateDepartment(id, department);
        return RestResult.success();
    }

    /**
     * 删除部门
     *
     * @param id 部门ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public RestResult<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return RestResult.success();
    }

    /**
     * 根据企业ID获取部门列表
     *
     * @param enterpriseId 企业ID
     * @return 部门列表
     */
    @GetMapping("/by-enterprise/{enterpriseId}")
    public RestResult<List<Department>> getDepartmentsByEnterprise(@PathVariable Long enterpriseId) {
        return RestResult.success(departmentService.getDepartmentsByEnterprise(enterpriseId));
    }
}
