package com.ps.user.service;

import com.ps.common.request.PageRequest;
import com.ps.common.response.PageResult;
import com.ps.core.entity.Enterprise;

import java.util.Map;

public interface EnterpriseService {

    Enterprise getEnterpriseById(Long id);

    Enterprise getEnterpriseByCode(String enterpriseCode);

    Enterprise createEnterprise(String name, String code, String contact, String phone, String email);

    void updateEnterprise(Long id, Map<String, Object> params);

    void disableEnterprise(Long id);

    void enableEnterprise(Long id);

    PageResult<Enterprise> getEnterpriseList(PageRequest request);
}
