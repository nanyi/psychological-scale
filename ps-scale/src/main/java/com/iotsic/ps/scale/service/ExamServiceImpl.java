package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.utils.EncryptUtils;
import com.iotsic.ps.core.entity.*;
import com.iotsic.ps.scale.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRecordMapper examRecordMapper;
    private final ExamAnswerMapper examAnswerMapper;
    private final ScaleMapper scaleMapper;
    private final QuestionMapper questionMapper;
    private final ScoringService scoringService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExamRecord startExam(Long userId, Long scaleId, String ipAddress, String deviceInfo) {
        Scale scale = scaleMapper.selectById(scaleId);
        if (scale == null || scale.getDeleted() == 1) {
            throw BusinessException.of("SCALE_NOT_FOUND", "量表不存在");
        }
        if (scale.getStatus() != 1) {
            throw BusinessException.of("SCALE_OFFLINE", "量表未发布");
        }

        ExamRecord record = new ExamRecord();
        record.setUserId(userId);
        record.setScaleId(scaleId);
        record.setRecordNo(EncryptUtils.generateUUID().substring(0, 16));
        record.setExamStatus(0);
        record.setIpAddress(ipAddress);
        record.setDeviceInfo(deviceInfo);
        record.setStartTime(LocalDateTime.now());
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());

        examRecordMapper.insert(record);

        scale.setUseCount(scale.getUseCount() + 1);
        scaleMapper.updateById(scale);

        return record;
    }

    @Override
    public ExamRecord getExamRecordById(Long id) {
        ExamRecord record = examRecordMapper.selectById(id);
        if (record == null || record.getDeleted() == 1) {
            throw BusinessException.of("RECORD_NOT_FOUND", "测评记录不存在");
        }
        return record;
    }

    @Override
    public ExamRecord getExamRecordByNo(String recordNo) {
        LambdaQueryWrapper<ExamRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamRecord::getRecordNo, recordNo);
        ExamRecord record = examRecordMapper.selectOne(wrapper);
        if (record == null || record.getDeleted() == 1) {
            throw BusinessException.of("RECORD_NOT_FOUND", "测评记录不存在");
        }
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAnswer(Long recordId, Map<Long, String> answers) {
        ExamRecord record = getExamRecordById(recordId);
        if (record.getExamStatus() != 0) {
            throw BusinessException.of("EXAM_FINISHED", "测评已结束");
        }

        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            Long questionId = entry.getKey();
            String answer = entry.getValue();

            LambdaQueryWrapper<ExamAnswer> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ExamAnswer::getRecordId, recordId)
                    .eq(ExamAnswer::getQuestionId, questionId);
            ExamAnswer examAnswer = examAnswerMapper.selectOne(wrapper);

            if (examAnswer != null) {
                examAnswer.setAnswer(answer);
                examAnswer.setAnswerTime(LocalDateTime.now());
                examAnswer.setUpdateTime(LocalDateTime.now());
                examAnswerMapper.updateById(examAnswer);
            } else {
                Question question = questionMapper.selectById(questionId);
                examAnswer = new ExamAnswer();
                examAnswer.setRecordId(recordId);
                examAnswer.setQuestionId(questionId);
                examAnswer.setQuestionCode(question != null ? question.getQuestionCode() : null);
                examAnswer.setAnswer(answer);
                examAnswer.setAnswerTime(LocalDateTime.now());
                examAnswer.setSort(question != null ? question.getSort() : 0);
                examAnswer.setCreateTime(LocalDateTime.now());
                examAnswer.setUpdateTime(LocalDateTime.now());
                examAnswerMapper.insert(examAnswer);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> submitExam(Long recordId) {
        ExamRecord record = getExamRecordById(recordId);
        if (record.getExamStatus() == 1) {
            throw BusinessException.of("EXAM_FINISHED", "测评已提交");
        }

        LambdaQueryWrapper<ExamAnswer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamAnswer::getRecordId, recordId);
        List<ExamAnswer> answers = examAnswerMapper.selectList(wrapper);

        Map<Long, String> answerMap = new HashMap<>();
        for (ExamAnswer answer : answers) {
            answerMap.put(answer.getQuestionId(), answer.getAnswer());
        }

        Map<String, Object> scoreResult = scoringService.calculateScore(record.getScaleId(), answerMap);
        BigDecimal totalScore = (BigDecimal) scoreResult.get("totalScore");
        Map<String, Object> dimensionScores = (Map<String, Object>) scoreResult.get("dimensionScores");

        record.setTotalScore(totalScore.intValue());
        record.setScore(totalScore);
        record.setExamStatus(1);
        record.setSubmitTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        examRecordMapper.updateById(record);

        Map<String, Object> result = new HashMap<>();
        result.put("record", record);
        result.put("totalScore", totalScore);
        result.put("dimensionScores", dimensionScores);
        
        String interpretation = scoringService.interpretScore(record.getScaleId(), dimensionScores);
        result.put("interpretation", interpretation);

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pauseExam(Long recordId) {
        ExamRecord record = getExamRecordById(recordId);
        if (record.getExamStatus() != 0) {
            throw BusinessException.of("EXAM_FINISHED", "测评已结束");
        }
        record.setExamStatus(2);
        record.setUpdateTime(LocalDateTime.now());
        examRecordMapper.updateById(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resumeExam(Long recordId) {
        ExamRecord record = getExamRecordById(recordId);
        if (record.getExamStatus() != 2) {
            throw BusinessException.of("EXAM_NOT_PAUSED", "测评未暂停");
        }
        record.setExamStatus(0);
        record.setUpdateTime(LocalDateTime.now());
        examRecordMapper.updateById(record);
    }

    @Override
    public PageResult<ExamRecord> getUserExamRecords(PageRequest request, Long userId) {
        Page<ExamRecord> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<ExamRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamRecord::getUserId, userId)
                .orderByDesc(ExamRecord::getCreateTime);
        IPage<ExamRecord> result = examRecordMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), request.getPageNum(), request.getPageSize());
    }

    @Override
    public ExamRecord getLatestExamRecord(Long userId, Long scaleId) {
        LambdaQueryWrapper<ExamRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamRecord::getUserId, userId)
                .eq(ExamRecord::getScaleId, scaleId)
                .orderByDesc(ExamRecord::getCreateTime)
                .last("LIMIT 1");
        return examRecordMapper.selectOne(wrapper);
    }

    @Override
    public Map<String, Object> getExamProgress(Long recordId) {
        ExamRecord record = getExamRecordById(recordId);

        LambdaQueryWrapper<ExamAnswer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamAnswer::getRecordId, recordId);
        List<ExamAnswer> answers = examAnswerMapper.selectList(wrapper);

        Scale scale = scaleMapper.selectById(record.getScaleId());
        int totalQuestions = scale != null ? scale.getQuestionCount() : 0;
        int answeredQuestions = answers.size();

        Map<String, Object> progress = new HashMap<>();
        progress.put("recordId", recordId);
        progress.put("status", record.getExamStatus());
        progress.put("totalQuestions", totalQuestions);
        progress.put("answeredQuestions", answeredQuestions);
        progress.put("progress", totalQuestions > 0 ? (answeredQuestions * 100 / totalQuestions) : 0);
        progress.put("startTime", record.getStartTime());

        return progress;
    }
}
