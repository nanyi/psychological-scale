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
 * 负责题目和维度的创建、查询、更新、删除等业务操作
 *
 * @author Ryan
 * @since 2026-03-12
 */
public interface QuestionService {

    /**
     * 根据ID获取题目
     *
     * @param id 题目ID
     * @return 题目实体
     */
    Question getQuestionById(Long id);

    /**
     * 根据量表ID获取题目列表
     *
     * @param scaleId 量表ID
     * @return 题目列表
     */
    List<Question> getQuestionsByScaleId(Long scaleId);

    /**
     * 创建题目
     *
     * @param request 题目创建请求
     * @return 创建的题目实体
     */
    Question createQuestion(QuestionCreateRequest request);

    /**
     * 更新题目
     *
     * @param id 题目ID
     * @param request 题目更新请求
     */
    void updateQuestion(Long id, QuestionUpdateRequest request);

    /**
     * 删除题目
     *
     * @param id 题目ID
     */
    void deleteQuestion(Long id);

    /**
     * 重新排序题目
     *
     * @param scaleId 量表ID
     * @param questionIds 题目ID列表
     */
    void reorderQuestions(Long scaleId, List<Long> questionIds);

    /**
     * 根据ID获取维度
     *
     * @param id 维度ID
     * @return 维度实体
     */
    Dimension getDimensionById(Long id);

    /**
     * 根据量表ID获取维度列表
     *
     * @param scaleId 量表ID
     * @return 维度列表
     */
    List<Dimension> getDimensionsByScaleId(Long scaleId);

    /**
     * 创建维度
     *
     * @param request 维度创建请求
     * @return 创建的维度实体
     */
    Dimension createDimension(DimensionCreateRequest request);

    /**
     * 更新维度
     *
     * @param id 维度ID
     * @param request 维度更新请求
     */
    void updateDimension(Long id, DimensionUpdateRequest request);

    /**
     * 删除维度
     *
     * @param id 维度ID
     */
    void deleteDimension(Long id);
}
