package com.iotsic.ps.scale.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.ExamRecord;

import java.util.Map;

/**
 * 测评服务接口
 * 负责测评开始、答题保存、提交、暂停、恢复等业务操作
 *
 * @author Ryan
 * @since 2026-03-12
 */
public interface ExamService {

    /**
     * 开始测评
     *
     * @param userId 用户ID
     * @param scaleId 量表ID
     * @param ipAddress IP地址
     * @param deviceInfo 设备信息
     * @return 测评记录
     */
    ExamRecord startExam(Long userId, Long scaleId, String ipAddress, String deviceInfo);

    /**
     * 根据ID获取测评记录
     *
     * @param id 记录ID
     * @return 测评记录
     */
    ExamRecord getExamRecordById(Long id);

    /**
     * 根据记录号获取测评记录
     *
     * @param recordNo 记录号
     * @return 测评记录
     */
    ExamRecord getExamRecordByNo(String recordNo);

    /**
     * 保存答案
     *
     * @param recordId 记录ID
     * @param answers 答案列表（题目ID -> 答案）
     */
    void saveAnswer(Long recordId, Map<Long, String> answers);

    /**
     * 提交测评
     *
     * @param recordId 记录ID
     * @return 提交结果
     */
    Map<String, Object> submitExam(Long recordId);

    /**
     * 暂停测评
     *
     * @param recordId 记录ID
     */
    void pauseExam(Long recordId);

    /**
     * 恢复测评
     *
     * @param recordId 记录ID
     */
    void resumeExam(Long recordId);

    /**
     * 获取用户测评记录列表
     *
     * @param request 分页请求
     * @param userId 用户ID
     * @return 测评记录分页结果
     */
    PageResult<ExamRecord> getUserExamRecords(PageRequest request, Long userId);

    /**
     * 获取用户最新测评记录
     *
     * @param userId 用户ID
     * @param scaleId 量表ID
     * @return 测评记录
     */
    ExamRecord getLatestExamRecord(Long userId, Long scaleId);

    /**
     * 获取测评进度
     *
     * @param recordId 记录ID
     * @return 进度信息
     */
    Map<String, Object> getExamProgress(Long recordId);
}
