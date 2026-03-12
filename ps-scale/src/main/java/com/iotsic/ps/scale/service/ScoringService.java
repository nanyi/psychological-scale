package com.iotsic.ps.scale.service;

import com.iotsic.ps.core.entity.OptionScore;
import com.iotsic.ps.core.entity.ScoringRule;
import com.iotsic.ps.scale.dto.OptionScoreCreateRequest;
import com.iotsic.ps.scale.dto.OptionScoreUpdateRequest;
import com.iotsic.ps.scale.dto.ScoreCalculateRequest;
import com.iotsic.ps.scale.dto.ScoreCalculateResponse;
import com.iotsic.ps.scale.dto.ScoreInterpretRequest;
import com.iotsic.ps.scale.dto.ScoreInterpretResponse;
import com.iotsic.ps.scale.dto.ScoringRuleCreateRequest;
import com.iotsic.ps.scale.dto.ScoringRuleUpdateRequest;

import java.util.List;

/**
 * 计分服务接口
 * 负责计分规则和选项分数的管理、分数计算、结果解读等业务操作
 *
 * @author Ryan
 * @since 2026-03-12
 */
public interface ScoringService {

    /**
     * 创建计分规则
     *
     * @param request 计分规则创建请求
     * @return 创建的计分规则实体
     */
    ScoringRule createScoringRule(ScoringRuleCreateRequest request);

    /**
     * 更新计分规则
     *
     * @param id 计分规则ID
     * @param request 计分规则更新请求
     */
    void updateScoringRule(Long id, ScoringRuleUpdateRequest request);

    /**
     * 删除计分规则
     *
     * @param id 计分规则ID
     */
    void deleteScoringRule(Long id);

    /**
     * 根据量表ID获取计分规则列表
     *
     * @param scaleId 量表ID
     * @return 计分规则列表
     */
    List<ScoringRule> getScoringRulesByScaleId(Long scaleId);

    /**
     * 根据维度ID获取计分规则列表
     *
     * @param dimensionId 维度ID
     * @return 计分规则列表
     */
    List<ScoringRule> getScoringRulesByDimensionId(Long dimensionId);

    /**
     * 创建选项分数
     *
     * @param request 选项分数创建请求
     * @return 创建的选项分数实体
     */
    OptionScore createOptionScore(OptionScoreCreateRequest request);

    /**
     * 更新选项分数
     *
     * @param id 选项分数ID
     * @param request 选项分数更新请求
     */
    void updateOptionScore(Long id, OptionScoreUpdateRequest request);

    /**
     * 删除选项分数
     *
     * @param id 选项分数ID
     */
    void deleteOptionScore(Long id);

    /**
     * 根据题目ID获取选项分数列表
     *
     * @param questionId 题目ID
     * @return 选项分数列表
     */
    List<OptionScore> getOptionScoresByQuestionId(Long questionId);

    /**
     * 计算分数
     *
     * @param request 分数计算请求
     * @return 计算结果
     */
    ScoreCalculateResponse calculateScore(ScoreCalculateRequest request);

    /**
     * 解读分数
     *
     * @param request 分数解读请求
     * @return 解读结果
     */
    ScoreInterpretResponse interpretScore(ScoreInterpretRequest request);
}
