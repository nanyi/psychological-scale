package com.iotsic.ps.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.core.entity.Department;
import com.iotsic.ps.user.mapper.DepartmentMapper;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门服务实现类
 * 
 * @author Ryan
 * @since 2026-03-18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;

    @Override
    public List<Department> getDepartmentTree() {
        List<Department> allDepartments = departmentMapper.selectList(null);
        return buildTree(allDepartments);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.selectList(null);
    }

    @Override
    public PageResult<Department> getDepartmentPage(PageRequest request, String departmentName, Integer status) {
        Page<Department> page = new Page<>(request.getCurrent() != null ? request.getCurrent() : 1,
                request.getSize() != null ? request.getSize() : 10);
        
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        if (departmentName != null && !departmentName.isEmpty()) {
            wrapper.like(Department::getDepartmentName, departmentName);
        }
        if (status != null) {
            wrapper.eq(Department::getStatus, status);
        }
        wrapper.orderByAsc(Department::getSortOrder);
        
        IPage<Department> result = departmentMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal());
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Department createDepartment(Department department) {
        department.setCreateTime(LocalDateTime.now());
        department.setUpdateTime(LocalDateTime.now());
        if (department.getStatus() == null) {
            department.setStatus(1);
        }
        if (department.getParentId() == null) {
            department.setParentId(0L);
        }
        if (department.getSortOrder() == null) {
            department.setSortOrder(0);
        }
        departmentMapper.insert(department);
        return department;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Department updateDepartment(Long id, Department department) {
        Department existing = departmentMapper.selectById(id);
        if (existing != null) {
            existing.setDepartmentName(department.getDepartmentName());
            existing.setParentId(department.getParentId());
            existing.setEnterpriseId(department.getEnterpriseId());
            existing.setLeader(department.getLeader());
            existing.setPhone(department.getPhone());
            existing.setEmail(department.getEmail());
            existing.setSortOrder(department.getSortOrder());
            existing.setStatus(department.getStatus());
            existing.setDescription(department.getDescription());
            existing.setUpdateTime(LocalDateTime.now());
            departmentMapper.updateById(existing);
        }
        return existing;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDepartment(Long id) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getParentId, id);
        Long count = departmentMapper.selectCount(wrapper);
        if (count > 0) {
            throw new RuntimeException("请先删除子部门");
        }
        departmentMapper.deleteById(id);
    }

    @Override
    public List<Department> getDepartmentsByEnterprise(Long enterpriseId) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getEnterpriseId, enterpriseId);
        wrapper.orderByAsc(Department::getSortOrder);
        return departmentMapper.selectList(wrapper);
    }

    private List<Department> buildTree(List<Department> departments) {
        List<Department> roots = departments.stream()
                .filter(d -> d.getParentId() == null || d.getParentId() == 0)
                .collect(Collectors.toList());
        
        for (Department root : roots) {
            root.setChildren(getChildren(root.getId(), departments));
        }
        return roots;
    }

    private List<Department> getChildren(Long parentId, List<Department> allDepartments) {
        List<Department> children = allDepartments.stream()
                .filter(d -> parentId.equals(d.getParentId()))
                .collect(Collectors.toList());
        
        for (Department child : children) {
            List<Department> subChildren = getChildren(child.getId(), allDepartments);
            if (subChildren != null && !subChildren.isEmpty()) {
                child.setChildren(subChildren);
            }
        }
        return children;
    }
}
