package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.core.entity.*;
import com.iotsic.ps.scale.dto.OptionScoreCreateRequest;
import com.iotsic.ps.scale.dto.OptionScoreUpdateRequest;
import com.iotsic.ps.scale.dto.ScoringRuleCreateRequest;
import com.iotsic.ps.scale.dto.ScoringRuleUpdateRequest;
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
    public ScoringRule createScoringRule(ScoringRuleCreateRequest request) {
        Scale scale = scaleMapper.selectById(request.getScaleId());
        if (scale == null || scale.getDeleted() == 1) {
            throw BusinessException.of("SCALE_NOT_FOUND", "量表不存在");
        }

        ScoringRule rule = new ScoringRule();
        rule.setScaleId(request.getScaleId());
        rule.setDimensionId(request.getDimensionId());
        rule.setRuleType(request.getScoringType());
        rule.setMinScore(new BigDecimal(request.getMinScore()));
        rule.setMaxScore(new BigDecimal(request.getMaxScore()));
        rule.setStatus(1);
        rule.setCreateTime(LocalDateTime.now());
        rule.setUpdateTime(LocalDateTime.now());

        scoringRuleMapper.insert(rule);
        return rule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateScoringRule(Long id, ScoringRuleUpdateRequest request) {
        ScoringRule rule = scoringRuleMapper.selectById(id);
        if (rule == null || rule.getDeleted() == 1) {
            throw BusinessException.of("RULE_NOT_FOUND", "评分规则不存在");
        }

        if (request.getScoringType() != null) {
            rule.setRuleType(request.getScoringType());
        }
        if (request.getMinScore() != null) {
            rule.setMinScore(new BigDecimal(request.getMinScore()));
        }
        if (request.getMaxScore() != null) {
            rule.setMaxScore(new BigDecimal(request.getMaxScore()));
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
    public OptionScore createOptionScore(OptionScoreCreateRequest request) {
        Question question = questionMapper.selectById(request.getQuestionId());
        if (question == null || question.getDeleted() == 1) {
            throw BusinessException.of("QUESTION_NOT_FOUND", "题目不存在");
        }

        OptionScore optionScore = new OptionScore();
        optionScore.setQuestionId(request.getQuestionId());
        optionScore.setOptionValue(request.getOptionValue());
        optionScore.setScore(BigDecimal.valueOf(request.getScore()));
        optionScore.setReverseScore(request.getReverseScore());
        optionScore.setDimensionCode(request.getDimensionCode());
        optionScore.setCreateTime(LocalDateTime.now());
        optionScore.setUpdateTime(LocalDateTime.now());

        optionScoreMapper.insert(optionScore);
        return optionScore;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOptionScore(Long id, OptionScoreUpdateRequest request) {
        OptionScore optionScore = optionScoreMapper.selectById(id);
        if (optionScore == null || optionScore.getDeleted() == 1) {
            throw BusinessException.of("OPTION_NOT_FOUND", "选项分数不存在");
        }

        if (request.getScore() != null) {
            optionScore.setScore(BigDecimal.valueOf(request.getScore()));
        }
        if (request.getReverseScore() != null) {
            optionScore.setReverseScore(request.getReverseScore());
        }
        if (request.getDimensionCode() != null) {
            optionScore.setDimensionCode(request.getDimensionCode());
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
