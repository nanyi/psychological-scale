package com.iotsic.ps.analysis.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 群体趋势响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class GroupTrendResponse {

    /**
     * 维度
     */
    private String dimension;

    /**
     * 时间点
     */
    private List<String> timePoints;

    /**
     * 趋势数据
     */
    private Map<String, List<Long>> trends;
}
