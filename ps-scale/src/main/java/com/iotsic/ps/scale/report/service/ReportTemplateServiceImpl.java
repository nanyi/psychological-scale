package com.iotsic.ps.scale.report.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.report.entity.ReportTemplate;
import com.iotsic.ps.report.mapper.ReportTemplateMapper;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportTemplateServiceImpl implements ReportTemplateService {

    private final ReportTemplateMapper reportTemplateMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReportTemplate createTemplate(ReportTemplate template) {
        template.setStatus(0);
        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());
        reportTemplateMapper.insert(template);
        return template;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReportTemplate updateTemplate(ReportTemplate template) {
        template.setUpdateTime(LocalDateTime.now());
        reportTemplateMapper.updateById(template);
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
        IPage<ReportTemplate> result = reportTemplateMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal());
    }

    @Override
    public ReportTemplate getTemplateById(Long templateId) {
        return reportTemplateMapper.selectById(templateId);
    }

    @Override
    public ReportTemplate getDefaultTemplate(Long scaleId) {
        return reportTemplateMapper.selectOne(new LambdaQueryWrapper<ReportTemplate>()
                .eq(ReportTemplate::getScaleId, scaleId)
                .eq(ReportTemplate::getStatus, 1)
                .orderByDesc(ReportTemplate::getTemplateType)
                .last("LIMIT 1"));
    }
}
