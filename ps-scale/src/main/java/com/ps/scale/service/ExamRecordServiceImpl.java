package com.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ps.common.exception.BusinessException;
import com.ps.common.request.PageRequest;
import com.ps.common.response.PageResult;
import com.ps.core.entity.*;
import com.ps.scale.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
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
            throw BusinessException.of("RECORD_NOT_FOUND", "答题记录不存在");
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
    public Map<String, Object> getRecordDetail(Long recordId) {
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

        List<Map<String, Object>> answerDetails = new ArrayList<>();
        for (ExamAnswer answer : answers) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("questionId", answer.getQuestionId());
            detail.put("questionCode", answer.getQuestionCode());
            
            Question question = questionMap.get(answer.getQuestionId());
            if (question != null) {
                detail.put("questionText", question.getQuestionText());
                detail.put("questionType", question.getQuestionType());
                detail.put("options", question.getOptions());
            }
            
            detail.put("answer", answer.getAnswer());
            detail.put("score", answer.getScore());
            detail.put("answerTime", answer.getAnswerTime());
            
            answerDetails.add(detail);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("record", record);
        result.put("scale", scale);
        result.put("answers", answerDetails);
        
        if (record.getDimensionScores() != null) {
            result.put("dimensionScores", record.getDimensionScores());
        }

        return result;
    }

    @Override
    public PageResult<ExamRecord> getRecordList(PageRequest request, Map<String, Object> params) {
        Page<ExamRecord> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<ExamRecord> wrapper = new LambdaQueryWrapper<>();
        
        if (params != null) {
            if (params.containsKey("userId") && params.get("userId") != null) {
                wrapper.eq(ExamRecord::getUserId, params.get("userId"));
            }
            if (params.containsKey("scaleId") && params.get("scaleId") != null) {
                wrapper.eq(ExamRecord::getScaleId, params.get("scaleId"));
            }
            if (params.containsKey("examStatus") && params.get("examStatus") != null) {
                wrapper.eq(ExamRecord::getExamStatus, params.get("examStatus"));
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
        
        return PageResult.of(result.getRecords(), result.getTotal(), request.getPageNum(), request.getPageSize());
    }

    @Override
    public Map<String, Object> getRecordStatistics(Long userId) {
        LambdaQueryWrapper<ExamRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExamRecord::getUserId, userId);
        
        List<ExamRecord> records = examRecordMapper.selectList(wrapper);
        
        int totalExams = records.size();
        int completedExams = (int) records.stream()
                .filter(r -> r.getExamStatus() != null && r.getExamStatus() == 1)
                .count();
        
        double avgScore = records.stream()
                .filter(r -> r.getScore() != null)
                .mapToDouble(r -> r.getScore().doubleValue())
                .average()
                .orElse(0.0);
        
        Map<Long, Long> scaleCountMap = records.stream()
                .collect(Collectors.groupingBy(ExamRecord::getScaleId, Collectors.counting()));
        
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalExams", totalExams);
        statistics.put("completedExams", completedExams);
        statistics.put("incompleteExams", totalExams - completedExams);
        statistics.put("avgScore", Math.round(avgScore * 100) / 100.0);
        statistics.put("scaleCountMap", scaleCountMap);
        
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
