package com.iotsic.ps.report.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.report.entity.ReportTemplate;

public interface ReportTemplateService {

    ReportTemplate createTemplate(ReportTemplate template);

    ReportTemplate updateTemplate(ReportTemplate template);

    PageResult<ReportTemplate> getTemplateList(PageRequest request, Long scaleId, Integer status);

    ReportTemplate getTemplateById(Long templateId);

    ReportTemplate getDefaultTemplate(Long scaleId);
}
