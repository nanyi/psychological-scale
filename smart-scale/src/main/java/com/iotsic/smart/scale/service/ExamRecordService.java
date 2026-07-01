package com.iotsic.smart.scale.service;

import com.iotsic.ps.core.entity.ExamAnswer;
import com.iotsic.ps.core.entity.ExamRecord;
import com.iotsic.smart.scale.dto.ExamRecordListRequest;
import com.iotsic.smart.scale.dto.RecordDetailResponse;
import com.iotsic.smart.scale.dto.RecordStatisticsResponse;
import com.iotsic.smart.framework.common.dto.request.PageRequest;
import com.iotsic.smart.framework.common.dto.response.PageResult;

import java.util.List;

/**
 * 测评记录服务接口
 * 
 * @author Ryan
 * @since 2026-03-12
 */
public interface ExamRecordService {

    ExamRecord getRecordById(Long id);

    List<ExamAnswer> getRecordAnswers(Long recordId);

    /**
     * 获取测评记录详情
     *
     * @param recordId 记录ID
     * @return 记录详情
     */
    RecordDetailResponse getRecordDetail(Long recordId);

    PageResult<ExamRecord> getRecordList(PageRequest request, ExamRecordListRequest examRecordListRequest);

    /**
     * 获取测评记录统计
     *
     * @param userId 用户ID
     * @return 记录统计
     */
    RecordStatisticsResponse getRecordStatistics(Long userId);

    void deleteRecord(Long id);

    void exportRecord(Long recordId);
}
