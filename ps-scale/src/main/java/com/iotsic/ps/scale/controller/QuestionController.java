package com.iotsic.ps.scale.controller;

import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.core.entity.Dimension;
import com.iotsic.ps.core.entity.Question;
import com.iotsic.ps.scale.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/scale")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/questions/{id}")
    public RestResult<Question> getQuestionById(@PathVariable Long id) {
        return RestResult.success(questionService.getQuestionById(id));
    }

    @GetMapping("/questions/scale/{scaleId}")
    public RestResult<List<Question>> getQuestionsByScaleId(@PathVariable Long scaleId) {
        return RestResult.success(questionService.getQuestionsByScaleId(scaleId));
    }

    @PostMapping("/questions/create")
    public RestResult<Question> createQuestion(@RequestBody Map<String, Object> params) {
        Question question = questionService.createQuestion(params);
        return RestResult.success("题目创建成功", question);
    }

    @PutMapping("/questions/{id}")
    public RestResult<Void> updateQuestion(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        questionService.updateQuestion(id, params);
        return RestResult.success();
    }

    @DeleteMapping("/questions/{id}")
    public RestResult<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return RestResult.success();
    }

    @PostMapping("/questions/reorder")
    public RestResult<Void> reorderQuestions(@RequestBody Map<String, Object> params) {
        Long scaleId = Long.valueOf(params.get("scaleId").toString());
        List<Long> questionIds = (List<Long>) params.get("questionIds");
        questionService.reorderQuestions(scaleId, questionIds);
        return RestResult.success();
    }

    @GetMapping("/dimensions/{id}")
    public RestResult<Dimension> getDimensionById(@PathVariable Long id) {
        return RestResult.success(questionService.getDimensionById(id));
    }

    @GetMapping("/dimensions/scale/{scaleId}")
    public RestResult<List<Dimension>> getDimensionsByScaleId(@PathVariable Long scaleId) {
        return RestResult.success(questionService.getDimensionsByScaleId(scaleId));
    }

    @PostMapping("/dimensions/create")
    public RestResult<Dimension> createDimension(@RequestBody Map<String, Object> params) {
        Dimension dimension = questionService.createDimension(params);
        return RestResult.success("维度创建成功", dimension);
    }

    @PutMapping("/dimensions/{id}")
    public RestResult<Void> updateDimension(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        questionService.updateDimension(id, params);
        return RestResult.success();
    }

    @DeleteMapping("/dimensions/{id}")
    public RestResult<Void> deleteDimension(@PathVariable Long id) {
        questionService.deleteDimension(id);
        return RestResult.success();
    }
}
