package com.iotsic.ps.common.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotsic.ps.common.constant.BusinessConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer current = 1;
    private Integer size = BusinessConstant.DEFAULT_PAGE_SIZE;

    public void setCurrent(Integer current) {
        if (current != null && current > 0) {
            this.current = current;
        }
    }

    public void setSize(Integer size) {
        if (size != null && size > 0) {
            this.size = Math.min(size, BusinessConstant.MAX_PAGE_SIZE);
        }
    }

    @JsonIgnore
    public Integer getOffset() {
        return (current - 1) * size;
    }
}
