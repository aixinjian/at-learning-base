package com.learning.base.response;

import com.learning.base.common.BaseSerializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentreListResponse<T> extends BaseSerializable {
    private static final long serialVersionUID = -7628952830016632166L;

    @ApiModelProperty("数据列表")
    private List<T> dataList;

}
