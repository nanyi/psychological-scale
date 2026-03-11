package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.utils.JsonUtils;
import com.iotsic.ps.core.entity.*;
import com.iotsic.ps.scale.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoringServiceImpl implements ScoringService {

    private final ScoringRuleMapper scoringRuleMapper;
    private final OptionScoreMapper optionScoreMapper;
    private final ScaleMapper scaleMapper;
    private final DimensionMapper dimensionMapper;
    private final QuestionMapper questionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScoringRule createScoringRule(Map<String, Object> params) {
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        
        Scale scale = scaleMapper.selectById(scaleId);
        if (scale == null || scale.getDeleted() == 1) {
            throw BusinessException.of("SCALE_NOT_FOUND", "量表不存在");
        }

        ScoringRule rule = new ScoringRule();
        rule.setScaleId(scaleId);
        rule.setDimensionId((Long) params.get("dimensionId"));
        rule.setRuleType((Integer) params.get("ruleType"));
        rule.setRuleConfig((String) params.get("ruleConfig"));
        rule.setMinScore((BigDecimal) params.get("minScore"));
        rule.setMaxScore((BigDecimal) params.get("maxScore"));
        rule.setWeight((BigDecimal) params.get("weight"));
        rule.setInterpretationRule((String) params.get("interpretationRule"));
        rule.setStatus(1);
        rule.setCreateTime(LocalDateTime.now());
        rule.setUpdateTime(LocalDateTime.now());

        scoringRuleMapper.insert(rule);
        return rule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateScoringRule(Long id, Map<String, Object> params) {
        ScoringRule rule = scoringRuleMapper.selectById(id);
        if (rule == null || rule.getDeleted() == 1) {
            throw BusinessException.of("RULE_NOT_FOUND", "评分规则不存在");
        }

        if (params.containsKey("ruleType")) {
            rule.setRuleType((Integer) params.get("ruleType"));
        }
        if (params.containsKey("ruleConfig")) {
            rule.setRuleConfig((String) params.get("ruleConfig"));
        }
        if (params.containsKey("minScore")) {
            rule.setMinScore((BigDecimal) params.get("minScore"));
        }
        if (params.containsKey("maxScore")) {
            rule.setMaxScore((BigDecimal) params.get("maxScore"));
        }
        if (params.containsKey("weight")) {
            rule.setWeight((BigDecimal) params.get("weight"));
        }
        if (params.containsKey("interpretationRule")) {
            rule.setInterpretationRule((String) params.get("interpretationRule"));
        }
        if (params.containsKey("status")) {
            rule.setStatus((Integer) params.get("status"));
        }

        rule.setUpdateTime(LocalDateTime.now());
        scoringRuleMapper.updateById(rule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteScoringRule(Long id) {
        ScoringRule rule = scoringRuleMapper.selectById(id);
        if (rule == null) {
            throw BusinessException.of("RULE_NOT_FOUND", "评分规则不存在");
        }
        rule.setDeleted(1);
        rule.setUpdateTime(LocalDateTime.now());
        scoringRuleMapper.updateById(rule);
    }

    @Override
    public List<ScoringRule> getScoringRulesByScaleId(Long scaleId) {
        LambdaQueryWrapper<ScoringRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoringRule::getScaleId, scaleId)
                .eq(ScoringRule::getStatus, 1);
        return scoringRuleMapper.selectList(wrapper);
    }

    @Override
    public List<ScoringRule> getScoringRulesByDimensionId(Long dimensionId) {
        LambdaQueryWrapper<ScoringRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoringRule::getDimensionId, dimensionId);
        return scoringRuleMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OptionScore createOptionScore(Map<String, Object> params) {
        Long questionId = Long.valueOf(params.get("questionId").toString());
        
        Question question = questionMapper.selectById(questionId);
        if (question == null || question.getDeleted() == 1) {
            throw BusinessException.of("QUESTION_NOT_FOUND", "题目不存在");
        }

        OptionScore optionScore = new OptionScore();
        optionScore.setQuestionId(questionId);
        optionScore.setOptionValue((String) params.get("optionValue"));
        optionScore.setScore((BigDecimal) params.get("score"));
        optionScore.setReverseScore((Integer) params.get("reverseScore"));
        optionScore.setDimensionCode((String) params.get("dimensionCode"));
        optionScore.setCreateTime(LocalDateTime.now());
        optionScore.setUpdateTime(LocalDateTime.now());

        optionScoreMapper.insert(optionScore);
        return optionScore;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOptionScore(Long id, Map<String, Object> params) {
        OptionScore optionScore = optionScoreMapper.selectById(id);
        if (optionScore == null || optionScore.getDeleted() == 1) {
            throw BusinessException.of("OPTION_NOT_FOUND", "选项分数不存在");
        }

        if (params.containsKey("score")) {
            optionScore.setScore((BigDecimal) params.get("score"));
        }
        if (params.containsKey("reverseScore")) {
            optionScore.setReverseScore((Integer) params.get("reverseScore"));
        }
        if (params.containsKey("dimensionCode")) {
            optionScore.setDimensionCode((String) params.get("dimensionCode"));
        }

        optionScore.setUpdateTime(LocalDateTime.now());
        optionScoreMapper.updateById(optionScore);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOptionScore(Long id) {
        OptionScore optionScore = optionScoreMapper.selectById(id);
        if (optionScore == null) {
            throw BusinessException.of("OPTION_NOT_FOUND", "选项分数不存在");
        }
        optionScore.setDeleted(1);
        optionScore.setUpdateTime(LocalDateTime.now());
        optionScoreMapper.updateById(optionScore);
    }

    @Override
    public List<OptionScore> getOptionScoresByQuestionId(Long questionId) {
        LambdaQueryWrapper<OptionScore> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OptionScore::getQuestionId, questionId);
        return optionScoreMapper.selectList(wrapper);
    }

    @Override
    public Map<String, Object> calculateScore(Long scaleId, Map<Long, String> answers) {
        Map<String, Object> result = new HashMap<>();
        
        List<Dimension> dimensions = dimensionMapper.selectList(
                new LambdaQueryWrapper<Dimension>().eq(Dimension::getScaleId, scaleId)
        );
        
        Map<String, BigDecimal> dimensionScores = new HashMap<>();
        BigDecimal totalScore = BigDecimal.ZERO;

        for (Dimension dimension : dimensions) {
            dimensionScores.put(dimension.getDimensionCode(), BigDecimal.ZERO);
        }

        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            Long questionId = entry.getKey();
            String answer = entry.getValue();

            Question question = questionMapper.selectById(questionId);
            if (question == null) continue;

            LambdaQueryWrapper<OptionScore> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OptionScore::getQuestionId, questionId)
                    .eq(OptionScore::getOptionValue, answer);
            OptionScore optionScore = optionScoreMapper.selectOne(wrapper);

            if (optionScore != null) {
                BigDecimal score = optionScore.getScore();
                
                if (question.getIsReverse() != null && question.getIsReverse() == 1 && optionScore.getReverseScore() != null) {
                    score = BigDecimal.valueOf(optionScore.getReverseScore());
                }

                if (optionScore.getDimensionCode() != null) {
                    BigDecimal currentScore = dimensionScores.get(optionScore.getDimensionCode());
                    dimensionScores.put(optionScore.getDimensionCode(), 
                            currentScore.add(score));
                }

                totalScore = totalScore.add(score);
            }
        }

        result.put("totalScore", totalScore);
        result.put("dimensionScores", dimensionScores);
        
        return result;
    }

    @Override
    public String interpretScore(Long scaleId, Map<String, Object> dimensionScores) {
        StringBuilder interpretation = new StringBuilder();
        
        List<Dimension> dimensions = dimensionMapper.selectList(
                new LambdaQueryWrapper<Dimension>().eq(Dimension::getScaleId, scaleId)
        );

        for (Dimension dimension : dimensions) {
            String code = dimension.getDimensionCode();
            BigDecimal score = (BigDecimal) dimensionScores.get(code);
            
            if (score != null && dimension.getInterpretation() != null) {
                interpretation.append(dimension.getDimensionName())
                        .append(": ")
                        .append(score.setScale(2, RoundingMode.HALF_UP))
                        .append("\n");
            }
        }

        return interpretation.toString();
    }
}
