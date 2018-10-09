package com.learning.base.response;

import lombok.Data;

import java.util.List;

@Data
public class PageResponseResult<T> extends ListResponse<T> {

    private long totalCount;

    public PageResponseResult(long totalCount,List<T> dataList) {
        super(dataList);
        this.totalCount = totalCount;
    }

}
