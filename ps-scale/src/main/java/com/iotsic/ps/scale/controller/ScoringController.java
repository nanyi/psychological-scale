package com.iotsic.ps.scale.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.core.entity.OptionScore;
import com.iotsic.ps.core.entity.ScoringRule;
import com.iotsic.ps.scale.dto.OptionScoreCreateRequest;
import com.iotsic.ps.scale.dto.OptionScoreUpdateRequest;
import com.iotsic.ps.scale.dto.ScoreCalculateResponse;
import com.iotsic.ps.scale.dto.ScoreInterpretResponse;
import com.iotsic.ps.scale.dto.ScoringRuleCreateRequest;
import com.iotsic.ps.scale.dto.ScoringRuleUpdateRequest;
import com.iotsic.ps.scale.service.ScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 计分规则控制器
 * 负责评分规则和选项分数的管理
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/scale/scoring")
@RequiredArgsConstructor
public class ScoringController {

    private final ScoringService scoringService;

    /**
     * 创建评分规则
     * 
     * @param request 评分规则创建请求
     * @return 评分规则
     */
    @PostMapping("/rule/create")
    public RestResult<ScoringRule> createScoringRule(@RequestBody ScoringRuleCreateRequest request) {
        ScoringRule rule = scoringService.createScoringRule(request);
        return RestResult.success("评分规则创建成功", rule);
    }

    /**
     * 更新评分规则
     * 
     * @param id 评分规则ID
     * @param request 评分规则更新请求
     * @return 操作结果
     */
    @PutMapping("/rule/update/{id}")
    public RestResult<Void> updateScoringRule(@PathVariable Long id, @RequestBody ScoringRuleUpdateRequest request) {
        scoringService.updateScoringRule(id, request);
        return RestResult.success();
    }

    /**
     * 删除评分规则
     * 
     * @param id 评分规则ID
     * @return 操作结果
     */
    @DeleteMapping("/rule/delete/{id}")
    public RestResult<Void> deleteScoringRule(@PathVariable Long id) {
        scoringService.deleteScoringRule(id);
        return RestResult.success();
    }

    /**
     * 获取量表下的所有评分规则
     * 
     * @param scaleId 量表ID
     * @return 评分规则列表
     */
    @GetMapping("/rules/by-scale/{scaleId}")
    public RestResult<List<ScoringRule>> getScoringRulesByScaleId(@PathVariable Long scaleId) {
        return RestResult.success(scoringService.getScoringRulesByScaleId(scaleId));
    }

    /**
     * 获取维度下的所有评分规则
     * 
     * @param dimensionId 维度ID
     * @return 评分规则列表
     */
    @GetMapping("/rules/by-dimension/{dimensionId}")
    public RestResult<List<ScoringRule>> getScoringRulesByDimensionId(@PathVariable Long dimensionId) {
        return RestResult.success(scoringService.getScoringRulesByDimensionId(dimensionId));
    }

    /**
     * 创建选项分数
     * 
     * @param request 选项分数创建请求
     * @return 选项分数
     */
    @PostMapping("/option/create")
    public RestResult<OptionScore> createOptionScore(@RequestBody OptionScoreCreateRequest request) {
        OptionScore optionScore = scoringService.createOptionScore(request);
        return RestResult.success("选项分数创建成功", optionScore);
    }

    /**
     * 更新选项分数
     * 
     * @param id 选项分数ID
     * @param request 选项分数更新请求
     * @return 操作结果
     */
    @PutMapping("/option/update/{id}")
    public RestResult<Void> updateOptionScore(@PathVariable Long id, @RequestBody OptionScoreUpdateRequest request) {
        scoringService.updateOptionScore(id, request);
        return RestResult.success();
    }

    /**
     * 删除选项分数
     * 
     * @param id 选项分数ID
     * @return 操作结果
     */
    @DeleteMapping("/option/delete/{id}")
    public RestResult<Void> deleteOptionScore(@PathVariable Long id) {
        scoringService.deleteOptionScore(id);
        return RestResult.success();
    }

    /**
     * 获取题目的所有选项分数
     * 
     * @param questionId 题目ID
     * @return 选项分数列表
     */
    @GetMapping("/options/by-question/{questionId}")
    public RestResult<List<OptionScore>> getOptionScoresByQuestionId(@PathVariable Long questionId) {
        return RestResult.success(scoringService.getOptionScoresByQuestionId(questionId));
    }

    /**
     * 计算分数
     * 
     * @param scaleId 量表ID
     * @param answers 答案
     * @return 分数计算结果
     */
    @PostMapping("/calculate")
    public RestResult<ScoreCalculateResponse> calculateScore(
            @RequestParam Long scaleId,
            @RequestParam Map<Long, String> answers) {
        Map<String, Object> result = scoringService.calculateScore(scaleId, answers);
        
        ScoreCalculateResponse response = new ScoreCalculateResponse();
        response.setTotalScore((Integer) result.get("totalScore"));
        @SuppressWarnings("unchecked")
        Map<String, Integer> dimensionScores = (Map<String, Integer>) result.get("dimensionScores");
        response.setDimensionScores(dimensionScores);
        response.setLevel((String) result.get("level"));
        response.setSuggestion((String) result.get("suggestion"));
        
        return RestResult.success(response);
    }

    /**
     * 解读分数
     * 
     * @param scaleId 量表ID
     * @param dimensionScores 维度分数
     * @return 分数解读结果
     */
    @PostMapping("/interpret")
    public RestResult<ScoreInterpretResponse> interpretScore(
            @RequestParam Long scaleId,
            @RequestParam Map<String, Object> dimensionScores) {
        String interpretation = scoringService.interpretScore(scaleId, dimensionScores);
        
        ScoreInterpretResponse response = new ScoreInterpretResponse();
        response.setInterpretation(interpretation);
        
        return RestResult.success(response);
    }
}
