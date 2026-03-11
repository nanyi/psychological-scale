package com.iotsic.ps.scale.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.core.entity.OptionScore;
import com.iotsic.ps.core.entity.ScoringRule;
import com.iotsic.ps.scale.service.ScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/scale/scoring")
@RequiredArgsConstructor
public class ScoringController {

    private final ScoringService scoringService;

    @PostMapping("/rule/create")
    public RestResult<ScoringRule> createScoringRule(@RequestBody Map<String, Object> params) {
        ScoringRule rule = scoringService.createScoringRule(params);
        return RestResult.success("评分规则创建成功", rule);
    }

    @PutMapping("/rule/{id}")
    public RestResult<Void> updateScoringRule(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        scoringService.updateScoringRule(id, params);
        return RestResult.success();
    }

    @DeleteMapping("/rule/{id}")
    public RestResult<Void> deleteScoringRule(@PathVariable Long id) {
        scoringService.deleteScoringRule(id);
        return RestResult.success();
    }

    @GetMapping("/rules/scale/{scaleId}")
    public RestResult<List<ScoringRule>> getScoringRulesByScaleId(@PathVariable Long scaleId) {
        return RestResult.success(scoringService.getScoringRulesByScaleId(scaleId));
    }

    @GetMapping("/rules/dimension/{dimensionId}")
    public RestResult<List<ScoringRule>> getScoringRulesByDimensionId(@PathVariable Long dimensionId) {
        return RestResult.success(scoringService.getScoringRulesByDimensionId(dimensionId));
    }

    @PostMapping("/option/create")
    public RestResult<OptionScore> createOptionScore(@RequestBody Map<String, Object> params) {
        OptionScore optionScore = scoringService.createOptionScore(params);
        return RestResult.success("选项分数创建成功", optionScore);
    }

    @PutMapping("/option/{id}")
    public RestResult<Void> updateOptionScore(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        scoringService.updateOptionScore(id, params);
        return RestResult.success();
    }

    @DeleteMapping("/option/{id}")
    public RestResult<Void> deleteOptionScore(@PathVariable Long id) {
        scoringService.deleteOptionScore(id);
        return RestResult.success();
    }

    @GetMapping("/options/question/{questionId}")
    public RestResult<List<OptionScore>> getOptionScoresByQuestionId(@PathVariable Long questionId) {
        return RestResult.success(scoringService.getOptionScoresByQuestionId(questionId));
    }

    @PostMapping("/calculate")
    public RestResult<Map<String, Object>> calculateScore(@RequestBody Map<String, Object> params) {
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        Map<Long, String> answers = (Map<Long, String>) params.get("answers");
        
        Map<String, Object> result = scoringService.calculateScore(scaleId, answers);
        return RestResult.success(result);
    }

    @PostMapping("/interpret")
    public RestResult<Map<String, Object>> interpretScore(@RequestBody Map<String, Object> params) {
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        Map<String, Object> dimensionScores = (Map<String, Object>) params.get("dimensionScores");
        
        String interpretation = scoringService.interpretScore(scaleId, dimensionScores);
        Map<String, Object> result = Map.of("interpretation", interpretation);
        return RestResult.success(result);
    }
}
