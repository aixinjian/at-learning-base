package com.learning.base.conditon;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public abstract class BaseCondition {

    @Getter
    private int pageSize = 0;
    @Getter
    private int pageNum = 0;
    @Getter
    private int skipResults = 0;
    @Getter
    @Setter
    private String orderBy;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        skipResults = pageSize * pageNum;
    }

    public void setPageNum(int pageNo) {
        this.pageNum = pageNo;
        skipResults = pageSize * pageNo;
    }





}
