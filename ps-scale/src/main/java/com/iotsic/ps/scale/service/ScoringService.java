package com.iotsic.ps.scale.service;

import com.iotsic.ps.core.entity.OptionScore;
import com.iotsic.ps.core.entity.ScoringRule;
import com.iotsic.ps.scale.dto.OptionScoreCreateRequest;
import com.iotsic.ps.scale.dto.OptionScoreUpdateRequest;
import com.iotsic.ps.scale.dto.ScoringRuleCreateRequest;
import com.iotsic.ps.scale.dto.ScoringRuleUpdateRequest;

import java.util.List;
import java.util.Map;

/**
 * 计分服务接口
 * 
 * @author Ryan
 * @since 2026-03-12
 */
public interface ScoringService {

    ScoringRule createScoringRule(ScoringRuleCreateRequest request);

    void updateScoringRule(Long id, ScoringRuleUpdateRequest request);

    void deleteScoringRule(Long id);

    List<ScoringRule> getScoringRulesByScaleId(Long scaleId);

    List<ScoringRule> getScoringRulesByDimensionId(Long dimensionId);

    OptionScore createOptionScore(OptionScoreCreateRequest request);

    void updateOptionScore(Long id, OptionScoreUpdateRequest request);

    void deleteOptionScore(Long id);

    List<OptionScore> getOptionScoresByQuestionId(Long questionId);

    Map<String, Object> calculateScore(Long scaleId, Map<Long, String> answers);

    String interpretScore(Long scaleId, Map<String, Object> dimensionScores);
}
