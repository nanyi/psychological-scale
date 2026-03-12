package com.iotsic.ps.api.dto;

import com.iotsic.ps.core.entity.Dimension;
import lombok.Data;
import java.util.List;

/**
 * 维度列表响应DTO
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Data
public class DimensionListResponse {

    /**
     * 维度列表
     */
    private List<Dimension> dimensions;

    /**
     * 总数
     */
    private Integer totalCount;
}
