package com.iotsic.ps.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iotsic.ps.report.entity.Report;

public interface ReportService extends IService<Report> {

    Report generateReport(Long taskId, Long templateId);

    Report getReportByTaskId(Long taskId);

    IPage<Report> getReportList(Page<Report> page, Long userId, Long scaleId, Integer status);

    Report getReportDetail(Long reportId);

    void updateReportStatus(Long reportId, Integer status);
}
