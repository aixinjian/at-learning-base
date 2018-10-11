package com.learning.base.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class CentreCutPageResponse<T> extends CentreListResponse<T> {

    private static final long serialVersionUID = 5888709607809204814L;

    @ApiModelProperty("当前页码，第一页为0")
    private int pageNum;

    @ApiModelProperty("每页条数")
    private int pageSize;

    @ApiModelProperty("总条数")
    private long totalCount;

    public CentreCutPageResponse(int pageNum, int pageSize, long totalCount, List<T> dataList) {
        super(dataList);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }
}
