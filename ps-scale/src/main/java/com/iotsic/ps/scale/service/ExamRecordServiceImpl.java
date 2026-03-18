package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.ExamAnswer;
import com.iotsic.ps.core.entity.ExamRecord;
import com.iotsic.ps.core.entity.Question;
import com.iotsic.ps.core.entity.Scale;
import com.iotsic.ps.scale.dto.ExamRecordListRequest;
import com.iotsic.ps.scale.dto.RecordDetailResponse;
import com.iotsic.ps.scale.dto.RecordStatisticsResponse;
import com.iotsic.ps.scale.mapper.DimensionMapper;
import com.iotsic.ps.scale.mapper.ExamAnswerMapper;
import com.iotsic.ps.scale.mapper.ExamRecordMapper;
import com.iotsic.ps.scale.mapper.QuestionMapper;
import com.iotsic.ps.scale.mapper.ScaleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamRecordServiceImpl implements ExamRecordService {

    private final ExamRecordMapper examRecordMapper;
    private final ExamAnswerMapper examAnswerMapper;
    private final ScaleMapper scaleMapper;
    private final DimensionMapper dimensionMapper;
    private final QuestionMapper questionMapper;

    @Override
    public ExamRecord getRecordById(Long id) {
        ExamRecord record = examRecordMapper.selectById(id);
        if (record == null || record.getDeleted() == 1) {
            throw BusinessException.of(ErrorCodeEnum.RECORD_NOT_FOUND.getCode(), "答题记录不存在");
        }
        return record;
    }

    @Override
    public List<ExamAnswer> getRecordAnswers(Long recordId) {
        LambdaQueryWrapper<ExamAnswer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamAnswer::getRecordId, recordId)
                .orderByAsc(ExamAnswer::getSort);
        return examAnswerMapper.selectList(wrapper);
    }

    @Override
    public RecordDetailResponse getRecordDetail(Long recordId) {
        ExamRecord record = getRecordById(recordId);
        
        Scale scale = scaleMapper.selectById(record.getScaleId());
        
        LambdaQueryWrapper<ExamAnswer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamAnswer::getRecordId, recordId)
                .orderByAsc(ExamAnswer::getSort);
        List<ExamAnswer> answers = examAnswerMapper.selectList(wrapper);

        List<Long> questionIds = answers.stream()
                .map(ExamAnswer::getQuestionId)
                .collect(Collectors.toList());
        
        Map<Long, Question> questionMap = new HashMap<>();
        if (!questionIds.isEmpty()) {
            List<Question> questions = questionMapper.selectBatchIds(questionIds);
            questionMap = questions.stream()
                    .collect(Collectors.toMap(Question::getId, q -> q));
        }

        RecordDetailResponse result = new RecordDetailResponse();
        result.setRecordId(record.getId());
        result.setRecordNo(record.getRecordNo());
        result.setUserId(record.getUserId());
        result.setScaleId(record.getScaleId());
        result.setScaleName(scale != null ? scale.getScaleName() : null);
        result.setTotalScore(record.getScore());
        result.setStatus(record.getExamStatus());
        result.setStartTime(record.getStartTime());
        result.setEndTime(record.getSubmitTime());
        result.setDuration(record.getAnswerTime());
        
        List<RecordDetailResponse.AnswerDetail> answerDetails = new ArrayList<>();
        for (ExamAnswer answer : answers) {
            RecordDetailResponse.AnswerDetail detail = new RecordDetailResponse.AnswerDetail();
            detail.setQuestionId(answer.getQuestionId());
            
            Question question = questionMap.get(answer.getQuestionId());
            if (question != null) {
                detail.setQuestionContent(question.getQuestionText());
            }
            
            detail.setAnswer(answer.getAnswer());
            detail.setScore(answer.getScore() != null ? BigDecimal.valueOf(answer.getScore()) : null);
            answerDetails.add(detail);
        }
        result.setAnswers(answerDetails);

        return result;
    }

    @Override
    public PageResult<ExamRecord> getRecordList(PageRequest request, ExamRecordListRequest examRecordListRequest) {
        Page<ExamRecord> page = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<ExamRecord> wrapper = new LambdaQueryWrapper<>();
        
        if (examRecordListRequest != null) {
            if (examRecordListRequest.getUserId() != null) {
                wrapper.eq(ExamRecord::getUserId, examRecordListRequest.getUserId());
            }
            if (examRecordListRequest.getScaleId() != null) {
                wrapper.eq(ExamRecord::getScaleId, examRecordListRequest.getScaleId());
            }
            if (examRecordListRequest.getStatus() != null) {
                wrapper.eq(ExamRecord::getExamStatus, examRecordListRequest.getStatus());
            }
        }
        
        wrapper.orderByDesc(ExamRecord::getCreateTime);
        IPage<ExamRecord> result = examRecordMapper.selectPage(page, wrapper);
        
        for (ExamRecord record : result.getRecords()) {
            Scale scale = scaleMapper.selectById(record.getScaleId());
            if (scale != null) {
                record.setScale(scale);
            }
        }
        
        return PageResult.of(result.getRecords(), result.getTotal());
    }

    @Override
    public RecordStatisticsResponse getRecordStatistics(Long userId) {
        LambdaQueryWrapper<ExamRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamRecord::getUserId, userId);
        
        List<ExamRecord> records = examRecordMapper.selectList(wrapper);
        
        int totalExams = records.size();
        int completedExams = (int) records.stream()
                .filter(r -> r.getExamStatus() != null && r.getExamStatus() == 1)
                .count();
        
        double avgScoreVal = records.stream()
                .filter(r -> r.getScore() != null)
                .mapToDouble(r -> r.getScore().doubleValue())
                .average()
                .orElse(0.0);
        
        Map<Long, Long> scaleCountMap = records.stream()
                .collect(Collectors.groupingBy(ExamRecord::getScaleId, Collectors.counting()));
        
        RecordStatisticsResponse statistics = new RecordStatisticsResponse();
        statistics.setUserId(userId);
        statistics.setTotalExams(totalExams);
        statistics.setCompletedExams(completedExams);
        if (totalExams > 0) {
            statistics.setCompletionRate(BigDecimal.valueOf(completedExams * 100.0 / totalExams).setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            statistics.setCompletionRate(BigDecimal.ZERO);
        }
        statistics.setAvgScore(BigDecimal.valueOf(avgScoreVal).setScale(2, BigDecimal.ROUND_HALF_UP));
        statistics.setScaleCount(scaleCountMap.size());
        
        return statistics;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRecord(Long id) {
        ExamRecord record = getRecordById(id);
        
        LambdaQueryWrapper<ExamAnswer> answerWrapper = new LambdaQueryWrapper<>();
        answerWrapper.eq(ExamAnswer::getRecordId, id);
        examAnswerMapper.delete(answerWrapper);
        
        record.setDeleted(1);
        record.setUpdateTime(LocalDateTime.now());
        examRecordMapper.updateById(record);
    }

    @Override
    public void exportRecord(Long recordId) {
        getRecordDetail(recordId);
    }
}
