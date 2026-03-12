package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.utils.EncryptUtils;
import com.iotsic.ps.core.entity.*;
import com.iotsic.ps.scale.dto.ExamProgressResponse;
import com.iotsic.ps.scale.dto.ExamSubmitResultResponse;
import com.iotsic.ps.scale.dto.ScoreCalculateRequest;
import com.iotsic.ps.scale.dto.ScoreCalculateResponse;
import com.iotsic.ps.scale.dto.ScoreInterpretRequest;
import com.iotsic.ps.scale.dto.ScoreInterpretResponse;
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
    public ExamSubmitResultResponse submitExam(Long recordId) {
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

        ScoreCalculateRequest calculateRequest = new ScoreCalculateRequest();
        calculateRequest.setScaleId(record.getScaleId());
        calculateRequest.setAnswers(answerMap);
        ScoreCalculateResponse scoreResult = scoringService.calculateScore(calculateRequest);
        BigDecimal totalScore = BigDecimal.valueOf(scoreResult.getTotalScore());
        Map<String, Object> dimensionScores = new HashMap<>();
        if (scoreResult.getDimensionScores() != null) {
            for (Map.Entry<String, BigDecimal> entry : scoreResult.getDimensionScores().entrySet()) {
                dimensionScores.put(entry.getKey(), entry.getValue());
            }
        }

        record.setTotalScore(totalScore.intValue());
        record.setScore(totalScore);
        record.setExamStatus(1);
        record.setSubmitTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        examRecordMapper.updateById(record);

        ScoreInterpretRequest interpretRequest = new ScoreInterpretRequest();
        interpretRequest.setScaleId(record.getScaleId());
        interpretRequest.setDimensionScores(dimensionScores);
        interpretRequest.setTotalScore(totalScore.intValue());
        ScoreInterpretResponse interpretationResult = scoringService.interpretScore(interpretRequest);

        ExamSubmitResultResponse result = new ExamSubmitResultResponse();
        result.setRecordId(recordId);
        result.setRecordNo(record.getRecordNo());
        result.setTotalScore(totalScore);
        result.setDimensionScores(dimensionScores);
        result.setInterpretation(interpretationResult.getInterpretation());
        result.setSubmitTime(record.getSubmitTime());

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
    public ExamProgressResponse getExamProgress(Long recordId) {
        ExamRecord record = getExamRecordById(recordId);

        LambdaQueryWrapper<ExamAnswer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamAnswer::getRecordId, recordId);
        List<ExamAnswer> answers = examAnswerMapper.selectList(wrapper);

        Scale scale = scaleMapper.selectById(record.getScaleId());
        int totalQuestions = scale != null ? scale.getQuestionCount() : 0;
        int answeredQuestions = answers.size();

        ExamProgressResponse progress = new ExamProgressResponse();
        progress.setRecordId(recordId);
        progress.setRecordNo(record.getRecordNo());
        progress.setTotalQuestions(totalQuestions);
        progress.setAnsweredQuestions(answeredQuestions);
        if (totalQuestions > 0) {
            progress.setProgressPercent(BigDecimal.valueOf(answeredQuestions * 100.0 / totalQuestions));
        } else {
            progress.setProgressPercent(BigDecimal.ZERO);
        }
        progress.setCurrentQuestionNo(answeredQuestions + 1);
        progress.setStatus(record.getExamStatus());

        return progress;
    }
}
