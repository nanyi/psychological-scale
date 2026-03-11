package com.iotsic.ps.report.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iotsic.ps.report.entity.ReportTemplate;
import com.iotsic.ps.report.mapper.ReportTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportTemplateServiceImpl extends ServiceImpl<ReportTemplateMapper, ReportTemplate> implements ReportTemplateService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReportTemplate createTemplate(ReportTemplate template) {
        template.setStatus(0);
        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());
        this.save(template);
        return template;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReportTemplate updateTemplate(ReportTemplate template) {
        template.setUpdateTime(LocalDateTime.now());
        this.updateById(template);
        return template;
    }

    @Override
    public IPage<ReportTemplate> getTemplateList(Page<ReportTemplate> page, Long scaleId, Integer status) {
        LambdaQueryWrapper<ReportTemplate> wrapper = new LambdaQueryWrapper<>();
        if (scaleId != null) {
            wrapper.eq(ReportTemplate::getScaleId, scaleId);
        }
        if (status != null) {
            wrapper.eq(ReportTemplate::getStatus, status);
        }
        wrapper.orderByDesc(ReportTemplate::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public ReportTemplate getTemplateById(Long templateId) {
        return this.getById(templateId);
    }

    @Override
    public ReportTemplate getDefaultTemplate(Long scaleId) {
        return this.getOne(new LambdaQueryWrapper<ReportTemplate>()
                .eq(ReportTemplate::getScaleId, scaleId)
                .eq(ReportTemplate::getStatus, 1)
                .orderByDesc(ReportTemplate::getTemplateType)
                .last("LIMIT 1"));
    }
}
