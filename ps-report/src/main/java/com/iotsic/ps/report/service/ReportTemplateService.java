package com.iotsic.ps.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iotsic.ps.report.entity.ReportTemplate;

public interface ReportTemplateService extends IService<ReportTemplate> {

    ReportTemplate createTemplate(ReportTemplate template);

    ReportTemplate updateTemplate(ReportTemplate template);

    IPage<ReportTemplate> getTemplateList(Page<ReportTemplate> page, Long scaleId, Integer status);

    ReportTemplate getTemplateById(Long templateId);

    ReportTemplate getDefaultTemplate(Long scaleId);
}
