package com.iotsic.ps.api.dto;

import lombok.Data;

import java.util.List;

/**
 * 量表列表响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class ScaleListResponse {

    /**
     * 量表列表
     */
    private List<ScaleResponse> scales;

    /**
     * 总数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;
}
