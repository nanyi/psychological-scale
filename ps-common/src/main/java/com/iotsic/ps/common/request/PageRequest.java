package com.iotsic.ps.common.request;

import com.iotsic.ps.common.constant.BusinessConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer current = 1;
    private Integer size = BusinessConstant.DEFAULT_PAGE_SIZE;

    public void setPageNum(Integer current) {
        if (current != null && current > 0) {
            this.current = current;
        }
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize != null && pageSize > 0) {
            this.size = Math.min(pageSize, BusinessConstant.MAX_PAGE_SIZE);
        }
    }

    public Integer getOffset() {
        return (current - 1) * size;
    }
}
