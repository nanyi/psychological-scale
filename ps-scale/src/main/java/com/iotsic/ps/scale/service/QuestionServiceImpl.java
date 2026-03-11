package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.utils.EncryptUtils;
import com.iotsic.ps.core.entity.Dimension;
import com.iotsic.ps.core.entity.Question;
import com.iotsic.ps.core.entity.Scale;
import com.iotsic.ps.scale.mapper.DimensionMapper;
import com.iotsic.ps.scale.mapper.QuestionMapper;
import com.iotsic.ps.scale.mapper.ScaleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;
    private final DimensionMapper dimensionMapper;
    private final ScaleMapper scaleMapper;

    @Override
    public Question getQuestionById(Long id) {
        Question question = questionMapper.selectById(id);
        if (question == null || question.getDeleted() == 1) {
            throw BusinessException.of("QUESTION_NOT_FOUND", "题目不存在");
        }
        return question;
    }

    @Override
    public List<Question> getQuestionsByScaleId(Long scaleId) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getScaleId, scaleId)
                .orderByAsc(Question::getSort);
        return questionMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Question createQuestion(Map<String, Object> params) {
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        
        Scale scale = scaleMapper.selectById(scaleId);
        if (scale == null || scale.getDeleted() == 1) {
            throw BusinessException.of("SCALE_NOT_FOUND", "量表不存在");
        }

        String questionCode = EncryptUtils.generateUUID().substring(0, 8);

        Question question = new Question();
        question.setScaleId(scaleId);
        question.setQuestionCode(questionCode);
        question.setQuestionText((String) params.get("questionText"));
        question.setQuestionType((Integer) params.get("questionType"));
        question.setDimensionId((Integer) params.get("dimensionId"));
        question.setSort((Integer) params.get("sort"));
        question.setIsRequired((Integer) params.get("isRequired"));
        question.setHelpText((String) params.get("helpText"));
        question.setIsReverse((Integer) params.get("isReverse"));
        question.setOptions((String) params.get("options"));
        question.setDisplayType((Integer) params.get("displayType"));
        question.setLogicRule((String) params.get("logicRule"));
        question.setCreateTime(LocalDateTime.now());
        question.setUpdateTime(LocalDateTime.now());

        questionMapper.insert(question);

        scale.setQuestionCount(scale.getQuestionCount() + 1);
        scaleMapper.updateById(scale);

        return question;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuestion(Long id, Map<String, Object> params) {
        Question question = getQuestionById(id);

        if (params.containsKey("questionText")) {
            question.setQuestionText((String) params.get("questionText"));
        }
        if (params.containsKey("questionType")) {
            question.setQuestionType((Integer) params.get("questionType"));
        }
        if (params.containsKey("dimensionId")) {
            question.setDimensionId((Integer) params.get("dimensionId"));
        }
        if (params.containsKey("sort")) {
            question.setSort((Integer) params.get("sort"));
        }
        if (params.containsKey("isRequired")) {
            question.setIsRequired((Integer) params.get("isRequired"));
        }
        if (params.containsKey("helpText")) {
            question.setHelpText((String) params.get("helpText"));
        }
        if (params.containsKey("isReverse")) {
            question.setIsReverse((Integer) params.get("isReverse"));
        }
        if (params.containsKey("options")) {
            question.setOptions((String) params.get("options"));
        }
        if (params.containsKey("displayType")) {
            question.setDisplayType((Integer) params.get("displayType"));
        }
        if (params.containsKey("logicRule")) {
            question.setLogicRule((String) params.get("logicRule"));
        }

        question.setUpdateTime(LocalDateTime.now());
        questionMapper.updateById(question);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQuestion(Long id) {
        Question question = getQuestionById(id);
        question.setDeleted(1);
        question.setUpdateTime(LocalDateTime.now());
        questionMapper.updateById(question);

        Scale scale = scaleMapper.selectById(question.getScaleId());
        if (scale != null && scale.getQuestionCount() > 0) {
            scale.setQuestionCount(scale.getQuestionCount() - 1);
            scaleMapper.updateById(scale);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reorderQuestions(Long scaleId, List<Long> questionIds) {
        for (int i = 0; i < questionIds.size(); i++) {
            Question question = questionMapper.selectById(questionIds.get(i));
            if (question != null && question.getScaleId().equals(scaleId)) {
                question.setSort(i + 1);
                question.setUpdateTime(LocalDateTime.now());
                questionMapper.updateById(question);
            }
        }
    }

    @Override
    public Dimension getDimensionById(Long id) {
        Dimension dimension = dimensionMapper.selectById(id);
        if (dimension == null || dimension.getDeleted() == 1) {
            throw BusinessException.of("DIMENSION_NOT_FOUND", "维度不存在");
        }
        return dimension;
    }

    @Override
    public List<Dimension> getDimensionsByScaleId(Long scaleId) {
        LambdaQueryWrapper<Dimension> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dimension::getScaleId, scaleId)
                .orderByAsc(Dimension::getSort);
        return dimensionMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dimension createDimension(Map<String, Object> params) {
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        
        Scale scale = scaleMapper.selectById(scaleId);
        if (scale == null || scale.getDeleted() == 1) {
            throw BusinessException.of("SCALE_NOT_FOUND", "量表不存在");
        }

        Dimension dimension = new Dimension();
        dimension.setScaleId(scaleId);
        dimension.setDimensionCode((String) params.get("dimensionCode"));
        dimension.setDimensionName((String) params.get("dimensionName"));
        dimension.setDimensionDesc((String) params.get("dimensionDesc"));
        dimension.setSort((Integer) params.get("sort"));
        dimension.setFormula((String) params.get("formula"));
        dimension.setWeight((java.math.BigDecimal) params.get("weight"));
        dimension.setInterpretation((String) params.get("interpretation"));
        dimension.setCreateTime(LocalDateTime.now());
        dimension.setUpdateTime(LocalDateTime.now());

        dimensionMapper.insert(dimension);

        scale.setDimensionCount(scale.getDimensionCount() + 1);
        scaleMapper.updateById(scale);

        return dimension;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDimension(Long id, Map<String, Object> params) {
        Dimension dimension = getDimensionById(id);

        if (params.containsKey("dimensionName")) {
            dimension.setDimensionName((String) params.get("dimensionName"));
        }
        if (params.containsKey("dimensionDesc")) {
            dimension.setDimensionDesc((String) params.get("dimensionDesc"));
        }
        if (params.containsKey("sort")) {
            dimension.setSort((Integer) params.get("sort"));
        }
        if (params.containsKey("formula")) {
            dimension.setFormula((String) params.get("formula"));
        }
        if (params.containsKey("weight")) {
            dimension.setWeight((java.math.BigDecimal) params.get("weight"));
        }
        if (params.containsKey("interpretation")) {
            dimension.setInterpretation((String) params.get("interpretation"));
        }

        dimension.setUpdateTime(LocalDateTime.now());
        dimensionMapper.updateById(dimension);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDimension(Long id) {
        Dimension dimension = getDimensionById(id);
        dimension.setDeleted(1);
        dimension.setUpdateTime(LocalDateTime.now());
        dimensionMapper.updateById(dimension);

        Scale scale = scaleMapper.selectById(dimension.getScaleId());
        if (scale != null && scale.getDimensionCount() > 0) {
            scale.setDimensionCount(scale.getDimensionCount() - 1);
            scaleMapper.updateById(scale);
        }
    }
}
