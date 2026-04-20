package com.iotsic.ps.scale.report.service;

import com.iotsic.ps.scale.report.entity.ReportTemplate;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;

public interface ReportTemplateService {

    ReportTemplate createTemplate(ReportTemplate template);

    ReportTemplate updateTemplate(ReportTemplate template);

    PageResult<ReportTemplate> getTemplateList(PageRequest request, Long scaleId, Integer status);

    ReportTemplate getTemplateById(Long templateId);

    ReportTemplate getDefaultTemplate(Long scaleId);
}