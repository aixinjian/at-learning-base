package com.learning.base.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * Created with of666.
 * User: of666
 * Date: 2015/10/30 0030
 * Time: 16:57
 */
@Data
public class BaseQueryForm {
    @ApiModelProperty("分页每页条数")
    public Integer pageSize = 20;

    @ApiModelProperty("分页页码")
    public Integer pageNum = 0;

    @ApiModelProperty(value = "排序字段")
    public List<SortInfo> sortInfos;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseForm{");
        sb.append("pageSize=").append(pageSize);
        sb.append(", pageNum=").append(pageNum);
        sb.append('}');
        return sb.toString();
    }
}
