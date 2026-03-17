package com.iotsic.ps.report.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.UserGroup;
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
    public PageResult<ReportTemplate> getTemplateList(PageRequest request, Long scaleId, Integer status) {
        Page<ReportTemplate> page= new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<ReportTemplate> wrapper = new LambdaQueryWrapper<>();
        if (scaleId != null) {
            wrapper.eq(ReportTemplate::getScaleId, scaleId);
        }
        if (status != null) {
            wrapper.eq(ReportTemplate::getStatus, status);
        }
        wrapper.orderByDesc(ReportTemplate::getCreateTime);
        IPage<ReportTemplate> result = this.page(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal());
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
