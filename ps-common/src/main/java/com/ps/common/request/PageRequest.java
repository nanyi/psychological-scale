package com.ps.common.request;

import com.ps.common.constant.BusinessConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageNum = 1;
    private Integer pageSize = BusinessConstant.DEFAULT_PAGE_SIZE;

    public void setPageNum(Integer pageNum) {
        if (pageNum != null && pageNum > 0) {
            this.pageNum = pageNum;
        }
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize != null && pageSize > 0) {
            this.pageSize = Math.min(pageSize, BusinessConstant.MAX_PAGE_SIZE);
        }
    }

    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
