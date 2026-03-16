package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.utils.EncryptUtils;
import com.iotsic.ps.core.entity.Dimension;
import com.iotsic.ps.core.entity.Question;
import com.iotsic.ps.core.entity.Scale;
import com.iotsic.ps.scale.dto.DimensionCreateRequest;
import com.iotsic.ps.scale.dto.DimensionUpdateRequest;
import com.iotsic.ps.scale.dto.QuestionCreateRequest;
import com.iotsic.ps.scale.dto.QuestionUpdateRequest;
import com.iotsic.ps.scale.mapper.DimensionMapper;
import com.iotsic.ps.scale.mapper.QuestionMapper;
import com.iotsic.ps.scale.mapper.ScaleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
            throw BusinessException.of(ErrorCodeEnum.QUESTION_NOT_FOUND.getCode(), "题目不存在");
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
    public Question createQuestion(QuestionCreateRequest request) {
        Scale scale = scaleMapper.selectById(request.getScaleId());
        if (scale == null || scale.getDeleted() == 1) {
            throw BusinessException.of(ErrorCodeEnum.SCALE_NOT_FOUND.getCode(), "量表不存在");
        }

        String questionCode = EncryptUtils.generateUUID().substring(0, 8);

        Question question = new Question();
        question.setScaleId(request.getScaleId());
        question.setQuestionCode(questionCode);
        question.setQuestionText(request.getContent());
        question.setQuestionType(request.getQuestionType());
        question.setDimensionId(request.getDimensionId());
        question.setSort(request.getSortOrder());
        question.setIsRequired(request.getRequired() ? 1 : 0);
        question.setOptions(request.getOptions());
        question.setDisplayType(1);
        question.setCreateTime(LocalDateTime.now());
        question.setUpdateTime(LocalDateTime.now());

        questionMapper.insert(question);

        scale.setQuestionCount(scale.getQuestionCount() + 1);
        scaleMapper.updateById(scale);

        return question;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuestion(Long id, QuestionUpdateRequest request) {
        Question question = getQuestionById(id);

        if (request.getContent() != null) {
            question.setQuestionText(request.getContent());
        }
        if (request.getQuestionType() != null) {
            question.setQuestionType(request.getQuestionType());
        }
        if (request.getOptions() != null) {
            question.setOptions(request.getOptions());
        }
        if (request.getRequired() != null) {
            question.setIsRequired(request.getRequired() ? 1 : 0);
        }
        if (request.getSortOrder() != null) {
            question.setSort(request.getSortOrder());
        }
        if (request.getGroupName() != null) {
            question.setHelpText(request.getGroupName());
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
            throw BusinessException.of(ErrorCodeEnum.DIMENSION_NOT_FOUND.getCode(), "维度不存在");
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
    public Dimension createDimension(DimensionCreateRequest request) {
        Scale scale = scaleMapper.selectById(request.getScaleId());
        if (scale == null || scale.getDeleted() == 1) {
            throw BusinessException.of(ErrorCodeEnum.SCALE_NOT_FOUND.getCode(), "量表不存在");
        }

        Dimension dimension = new Dimension();
        dimension.setScaleId(request.getScaleId());
        dimension.setDimensionCode(EncryptUtils.generateUUID().substring(0, 8));
        dimension.setDimensionName(request.getName());
        dimension.setDimensionDesc(request.getDescription());
        dimension.setSort(request.getSortOrder());
        dimension.setCreateTime(LocalDateTime.now());
        dimension.setUpdateTime(LocalDateTime.now());

        dimensionMapper.insert(dimension);

        scale.setDimensionCount(scale.getDimensionCount() + 1);
        scaleMapper.updateById(scale);

        return dimension;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDimension(Long id, DimensionUpdateRequest request) {
        Dimension dimension = getDimensionById(id);

        if (request.getName() != null) {
            dimension.setDimensionName(request.getName());
        }
        if (request.getDescription() != null) {
            dimension.setDimensionDesc(request.getDescription());
        }
        if (request.getSortOrder() != null) {
            dimension.setSort(request.getSortOrder());
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
