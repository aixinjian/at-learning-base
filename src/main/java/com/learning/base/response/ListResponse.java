package com.learning.base.response;


import com.learning.base.common.BaseSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListResponse<T> extends BaseSerializable {
    private List<T> dataList;

}
