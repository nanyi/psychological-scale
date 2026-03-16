package com.iotsic.ps.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.Enterprise;
import com.iotsic.ps.user.dto.EnterpriseUpdateRequest;
import com.iotsic.ps.user.mapper.EnterpriseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 企业服务实现类
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseMapper enterpriseMapper;

    @Override
    public Enterprise getEnterpriseById(Long id) {
        Enterprise enterprise = enterpriseMapper.selectById(id);
        if (enterprise == null || enterprise.getDeleted() == 1) {
            throw BusinessException.of(ErrorCodeEnum.ENTERPRISE_NOT_FOUND.getCode(), "企业不存在");
        }
        return enterprise;
    }

    @Override
    public Enterprise getEnterpriseByCode(String enterpriseCode) {
        LambdaQueryWrapper<Enterprise> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Enterprise::getEnterpriseCode, enterpriseCode);
        Enterprise enterprise = enterpriseMapper.selectOne(wrapper);
        if (enterprise == null || enterprise.getDeleted() == 1) {
            throw BusinessException.of(ErrorCodeEnum.ENTERPRISE_NOT_FOUND.getCode(), "企业不存在");
        }
        return enterprise;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Enterprise createEnterprise(String name, String code, String contactPerson, String contactPhone, String contactEmail) {
        LambdaQueryWrapper<Enterprise> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Enterprise::getEnterpriseCode, code);
        if (enterpriseMapper.selectOne(wrapper) != null) {
            throw BusinessException.of(ErrorCodeEnum.ENTERPRISE_EXIST.getCode(), "企业代码已存在");
        }

        Enterprise enterprise = new Enterprise();
        enterprise.setEnterpriseName(name);
        enterprise.setEnterpriseCode(code);
        enterprise.setContact(contactPerson);
        enterprise.setPhone(contactPhone);
        enterprise.setEmail(contactEmail);
        enterprise.setStatus(0);
        enterprise.setUserCount(0);
        enterprise.setScaleCount(0);
        enterprise.setCreateTime(LocalDateTime.now());
        enterprise.setUpdateTime(LocalDateTime.now());

        enterpriseMapper.insert(enterprise);
        return enterprise;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEnterprise(Long id, EnterpriseUpdateRequest request) {
        Enterprise enterprise = getEnterpriseById(id);

        if (request.getName() != null) {
            enterprise.setEnterpriseName(request.getName());
        }
        if (request.getCreditCode() != null) {
            enterprise.setEnterpriseCode(request.getCreditCode());
        }
        if (request.getContactPerson() != null) {
            enterprise.setContact(request.getContactPerson());
        }
        if (request.getContactPhone() != null) {
            enterprise.setPhone(request.getContactPhone());
        }
        if (request.getContactEmail() != null) {
            enterprise.setEmail(request.getContactEmail());
        }
        if (request.getAddress() != null) {
            enterprise.setAddress(request.getAddress());
        }

        enterprise.setUpdateTime(LocalDateTime.now());
        enterpriseMapper.updateById(enterprise);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disableEnterprise(Long id) {
        Enterprise enterprise = getEnterpriseById(id);
        enterprise.setStatus(1);
        enterprise.setUpdateTime(LocalDateTime.now());
        enterpriseMapper.updateById(enterprise);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enableEnterprise(Long id) {
        Enterprise enterprise = getEnterpriseById(id);
        enterprise.setStatus(0);
        enterprise.setUpdateTime(LocalDateTime.now());
        enterpriseMapper.updateById(enterprise);
    }

    @Override
    public PageResult<Enterprise> getEnterpriseList(PageRequest request) {
        Page<Enterprise> page = new Page<>(request.getCurrent(), request.getSize());
        IPage<Enterprise> result = enterpriseMapper.selectPage(page, null);
        return PageResult.of(result.getRecords(), result.getTotal());
    }
}
