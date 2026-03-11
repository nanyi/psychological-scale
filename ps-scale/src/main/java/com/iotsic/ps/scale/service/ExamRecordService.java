package com.iotsic.ps.scale.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.ExamAnswer;
import com.iotsic.ps.core.entity.ExamRecord;
import com.iotsic.ps.scale.dto.ExamRecordListRequest;

import java.util.List;
import java.util.Map;

/**
 * 测评记录服务接口
 * 
 * @author Ryan
 * @since 2026-03-12
 */
public interface ExamRecordService {

    ExamRecord getRecordById(Long id);

    List<ExamAnswer> getRecordAnswers(Long recordId);

    Map<String, Object> getRecordDetail(Long recordId);

    PageResult<ExamRecord> getRecordList(PageRequest request, ExamRecordListRequest examRecordListRequest);

    Map<String, Object> getRecordStatistics(Long userId);

    void deleteRecord(Long id);

    void exportRecord(Long recordId);
}
