package com.ps.scale.service;

import com.ps.common.request.PageRequest;
import com.ps.common.response.PageResult;
import com.ps.core.entity.ExamAnswer;
import com.ps.core.entity.ExamRecord;

import java.util.List;
import java.util.Map;

public interface ExamRecordService {

    ExamRecord getRecordById(Long id);

    List<ExamAnswer> getRecordAnswers(Long recordId);

    Map<String, Object> getRecordDetail(Long recordId);

    PageResult<ExamRecord> getRecordList(PageRequest request, Map<String, Object> params);

    Map<String, Object> getRecordStatistics(Long userId);

    void deleteRecord(Long id);

    void exportRecord(Long recordId);
}
