package com.iotsic.ps.report.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.Report;

public interface ReportService {

    Report generateReport(Long taskId, Long templateId);

    Report getReportByTaskId(Long taskId);

    PageResult<Report> getReportList(PageRequest request, Long userId, Long scaleId, Integer status);

    Report getReportDetail(Long reportId);

    void updateReportStatus(Long reportId, Integer status);
}
