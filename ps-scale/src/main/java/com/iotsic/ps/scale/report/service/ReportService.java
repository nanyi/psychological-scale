package com.iotsic.ps.report.service;

import com.iotsic.ps.core.entity.Report;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;

public interface ReportService {

    Report generateReport(Long taskId, Long templateId);

    Report getReportByTaskId(Long taskId);

    PageResult<Report> getReportList(PageRequest request, Long userId, Long scaleId, Integer status);

    Report getReportDetail(Long reportId);

    void updateReportStatus(Long reportId, Integer status);
}
