package com.iotsic.ps.scale.service;

import com.iotsic.ps.core.entity.OptionScore;
import com.iotsic.ps.core.entity.ScoringRule;

import java.util.List;
import java.util.Map;

public interface ScoringService {

    ScoringRule createScoringRule(Map<String, Object> params);

    void updateScoringRule(Long id, Map<String, Object> params);

    void deleteScoringRule(Long id);

    List<ScoringRule> getScoringRulesByScaleId(Long scaleId);

    List<ScoringRule> getScoringRulesByDimensionId(Long dimensionId);

    OptionScore createOptionScore(Map<String, Object> params);

    void updateOptionScore(Long id, Map<String, Object> params);

    void deleteOptionScore(Long id);

    List<OptionScore> getOptionScoresByQuestionId(Long questionId);

    Map<String, Object> calculateScore(Long scaleId, Map<Long, String> answers);

    String interpretScore(Long scaleId, Map<String, Object> dimensionScores);
}
