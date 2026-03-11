package com.ps.scale.service;

import com.ps.common.request.PageRequest;
import com.ps.common.response.PageResult;
import com.ps.core.entity.ExamAnswer;
import com.ps.core.entity.ExamRecord;
import com.ps.core.entity.Scale;

import java.util.Map;

public interface ExamService {

    ExamRecord startExam(Long userId, Long scaleId, String ipAddress, String deviceInfo);

    ExamRecord getExamRecordById(Long id);

    ExamRecord getExamRecordByNo(String recordNo);

    void saveAnswer(Long recordId, Map<Long, String> answers);

    Map<String, Object> submitExam(Long recordId);

    void pauseExam(Long recordId);

    void resumeExam(Long recordId);

    PageResult<ExamRecord> getUserExamRecords(PageRequest request, Long userId);

    ExamRecord getLatestExamRecord(Long userId, Long scaleId);

    Map<String, Object> getExamProgress(Long recordId);
}
