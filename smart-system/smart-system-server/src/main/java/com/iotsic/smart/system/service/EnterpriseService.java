package com.iotsic.smart.system.service;

import com.iotsic.ps.core.entity.Enterprise;
import com.iotsic.smart.system.dto.EnterpriseUpdateRequest;
import com.iotsic.smart.framework.common.dto.request.PageRequest;
import com.iotsic.smart.framework.common.dto.response.PageResult;

/**
 * 企业服务接口
 * 
 * @author Ryan
 * @since 2026-03-12
 */
public interface EnterpriseService {

    Enterprise getEnterpriseById(Long id);

    Enterprise getEnterpriseByCode(String enterpriseCode);

    Enterprise createEnterprise(String name, String code, String contactPerson, String contactPhone, String contactEmail);

    void updateEnterprise(Long id, EnterpriseUpdateRequest request);

    void disableEnterprise(Long id);

    void enableEnterprise(Long id);

    PageResult<Enterprise> getEnterpriseList(PageRequest request);
}
