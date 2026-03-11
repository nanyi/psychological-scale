package com.ps.scale.service;

import com.ps.core.entity.Question;
import com.ps.core.entity.Dimension;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    Question getQuestionById(Long id);

    List<Question> getQuestionsByScaleId(Long scaleId);

    Question createQuestion(Map<String, Object> params);

    void updateQuestion(Long id, Map<String, Object> params);

    void deleteQuestion(Long id);

    void reorderQuestions(Long scaleId, List<Long> questionIds);

    Dimension getDimensionById(Long id);

    List<Dimension> getDimensionsByScaleId(Long scaleId);

    Dimension createDimension(Map<String, Object> params);

    void updateDimension(Long id, Map<String, Object> params);

    void deleteDimension(Long id);
}
