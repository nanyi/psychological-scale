package com.iotsic.ps.scale.controller;

import com.iotsic.ps.core.entity.Dimension;
import com.iotsic.ps.core.entity.Question;
import com.iotsic.ps.scale.dto.DimensionCreateRequest;
import com.iotsic.ps.scale.dto.DimensionUpdateRequest;
import com.iotsic.ps.scale.dto.QuestionCreateRequest;
import com.iotsic.ps.scale.dto.QuestionReorderRequest;
import com.iotsic.ps.scale.dto.QuestionUpdateRequest;
import com.iotsic.ps.scale.service.QuestionService;
import com.iotsic.smart.framework.common.result.RestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 题目控制器
 * 负责题目和维度的CRUD操作
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/scale")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    /**
     * 根据ID获取题目
     * 
     * @param id 题目ID
     * @return 题目信息
     */
    @GetMapping("/questions/detail/{id}")
    public RestResult<Question> getQuestionById(@PathVariable Long id) {
        return RestResult.success(questionService.getQuestionById(id));
    }

    /**
     * 获取量表下的所有题目
     * 
     * @param scaleId 量表ID
     * @return 题目列表
     */
    @GetMapping("/questions/by-scale/{scaleId}")
    public RestResult<List<Question>> getQuestionsByScaleId(@PathVariable Long scaleId) {
        return RestResult.success(questionService.getQuestionsByScaleId(scaleId));
    }

    /**
     * 创建题目
     * 
     * @param request 题目创建请求
     * @return 题目信息
     */
    @PostMapping("/questions/create")
    public RestResult<Question> createQuestion(@RequestBody QuestionCreateRequest request) {
        Question question = questionService.createQuestion(request);
        return RestResult.success("题目创建成功", question);
    }

    /**
     * 更新题目
     * 
     * @param id 题目ID
     * @param request 题目更新请求
     * @return 操作结果
     */
    @PutMapping("/questions/update/{id}")
    public RestResult<Void> updateQuestion(@PathVariable Long id, @RequestBody QuestionUpdateRequest request) {
        questionService.updateQuestion(id, request);
        return RestResult.success();
    }

    /**
     * 删除题目
     * 
     * @param id 题目ID
     * @return 操作结果
     */
    @DeleteMapping("/questions/delete/{id}")
    public RestResult<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return RestResult.success();
    }

    /**
     * 题目排序
     * 
     * @param request 排序请求
     * @return 操作结果
     */
    @PostMapping("/questions/reorder")
    public RestResult<Void> reorderQuestions(@RequestBody QuestionReorderRequest request) {
        questionService.reorderQuestions(request.getScaleId(), request.getQuestionIds());
        return RestResult.success();
    }

    /**
     * 根据ID获取维度
     * 
     * @param id 维度ID
     * @return 维度信息
     */
    @GetMapping("/dimensions/detail/{id}")
    public RestResult<Dimension> getDimensionById(@PathVariable Long id) {
        return RestResult.success(questionService.getDimensionById(id));
    }

    /**
     * 获取量表下的所有维度
     * 
     * @param scaleId 量表ID
     * @return 维度列表
     */
    @GetMapping("/dimensions/by-scale/{scaleId}")
    public RestResult<List<Dimension>> getDimensionsByScaleId(@PathVariable Long scaleId) {
        return RestResult.success(questionService.getDimensionsByScaleId(scaleId));
    }

    /**
     * 创建维度
     * 
     * @param request 维度创建请求
     * @return 维度信息
     */
    @PostMapping("/dimensions/create")
    public RestResult<Dimension> createDimension(@RequestBody DimensionCreateRequest request) {
        Dimension dimension = questionService.createDimension(request);
        return RestResult.success("维度创建成功", dimension);
    }

    /**
     * 更新维度
     * 
     * @param id 维度ID
     * @param request 维度更新请求
     * @return 操作结果
     */
    @PutMapping("/dimensions/update/{id}")
    public RestResult<Void> updateDimension(@PathVariable Long id, @RequestBody DimensionUpdateRequest request) {
        questionService.updateDimension(id, request);
        return RestResult.success();
    }

    /**
     * 删除维度
     * 
     * @param id 维度ID
     * @return 操作结果
     */
    @DeleteMapping("/dimensions/delete/{id}")
    public RestResult<Void> deleteDimension(@PathVariable Long id) {
        questionService.deleteDimension(id);
        return RestResult.success();
    }
}
