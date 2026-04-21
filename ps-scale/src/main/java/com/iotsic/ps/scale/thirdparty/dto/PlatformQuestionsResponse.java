package com.iotsic.ps.scale.thirdparty.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 平台题目响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class PlatformQuestionsResponse {

    /**
     * 平台编码
     */
    private String platformCode;

    /**
     * 外部量表ID
     */
    private String externalScaleId;

    /**
     * 量表名称
     */
    private String scaleName;

    /**
     * 题目数量
     */
    private Integer questionCount;

    /**
     * 题目列表
     */
    private List<Map<String, Object>> questions;
}
