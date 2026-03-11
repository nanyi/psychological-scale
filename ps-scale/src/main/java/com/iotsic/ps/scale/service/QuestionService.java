package com.iotsic.ps.scale.service;

import com.iotsic.ps.core.entity.Question;
import com.iotsic.ps.core.entity.Dimension;
import com.iotsic.ps.scale.dto.DimensionCreateRequest;
import com.iotsic.ps.scale.dto.DimensionUpdateRequest;
import com.iotsic.ps.scale.dto.QuestionCreateRequest;
import com.iotsic.ps.scale.dto.QuestionUpdateRequest;

import java.util.List;

/**
 * 题目服务接口
 * 
 * @author Ryan
 * @since 2026-03-12
 */
public interface QuestionService {

    Question getQuestionById(Long id);

    List<Question> getQuestionsByScaleId(Long scaleId);

    Question createQuestion(QuestionCreateRequest request);

    void updateQuestion(Long id, QuestionUpdateRequest request);

    void deleteQuestion(Long id);

    void reorderQuestions(Long scaleId, List<Long> questionIds);

    Dimension getDimensionById(Long id);

    List<Dimension> getDimensionsByScaleId(Long scaleId);

    Dimension createDimension(DimensionCreateRequest request);

    void updateDimension(Long id, DimensionUpdateRequest request);

    void deleteDimension(Long id);
}
