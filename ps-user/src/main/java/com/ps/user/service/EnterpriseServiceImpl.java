package com.ps.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ps.common.exception.BusinessException;
import com.ps.common.request.PageRequest;
import com.ps.common.response.PageResult;
import com.ps.core.entity.Enterprise;
import com.ps.user.mapper.EnterpriseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseMapper enterpriseMapper;

    @Override
    public Enterprise getEnterpriseById(Long id) {
        Enterprise enterprise = enterpriseMapper.selectById(id);
        if (enterprise == null || enterprise.getDeleted() == 1) {
            throw BusinessException.of("ENTERPRISE_NOT_FOUND", "企业不存在");
        }
        return enterprise;
    }

    @Override
    public Enterprise getEnterpriseByCode(String enterpriseCode) {
        LambdaQueryWrapper<Enterprise> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Enterprise::getEnterpriseCode, enterpriseCode);
        Enterprise enterprise = enterpriseMapper.selectOne(wrapper);
        if (enterprise == null || enterprise.getDeleted() == 1) {
            throw BusinessException.of("ENTERPRISE_NOT_FOUND", "企业不存在");
        }
        return enterprise;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Enterprise createEnterprise(String name, String code, String contact, String phone, String email) {
        LambdaQueryWrapper<Enterprise> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Enterprise::getEnterpriseCode, code);
        if (enterpriseMapper.selectOne(wrapper) != null) {
            throw BusinessException.of("ENTERPRISE_EXIST", "企业代码已存在");
        }

        Enterprise enterprise = new Enterprise();
        enterprise.setEnterpriseName(name);
        enterprise.setEnterpriseCode(code);
        enterprise.setContact(contact);
        enterprise.setPhone(phone);
        enterprise.setEmail(email);
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
    public void updateEnterprise(Long id, Map<String, Object> params) {
        Enterprise enterprise = getEnterpriseById(id);

        if (params.containsKey("enterpriseName")) {
            enterprise.setEnterpriseName((String) params.get("enterpriseName"));
        }
        if (params.containsKey("contact")) {
            enterprise.setContact((String) params.get("contact"));
        }
        if (params.containsKey("phone")) {
            enterprise.setPhone((String) params.get("phone"));
        }
        if (params.containsKey("email")) {
            enterprise.setEmail((String) params.get("email"));
        }
        if (params.containsKey("address")) {
            enterprise.setAddress((String) params.get("address"));
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
        Page<Enterprise> page = new Page<>(request.getPageNum(), request.getPageSize());
        IPage<Enterprise> result = enterpriseMapper.selectPage(page, null);
        return PageResult.of(result.getRecords(), result.getTotal(), request.getPageNum(), request.getPageSize());
    }
}
