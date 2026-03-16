package com.iotsic.ps.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> records = new ArrayList<>();
    private Long total = 0L;

    public PageResult() {
    }

    public PageResult(List<T> records, Long total) {
        this.records = records;
        this.total = total;
    }

    public static <T> PageResult<T> of(List<T> records, Long total) {
        return new PageResult<>(records, total);
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(new ArrayList<>(), 0L);
    }
}
