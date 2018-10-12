package com.learning.base.response;


import com.learning.base.common.BaseSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListResponse<T> extends BaseSerializable {

    private List<T> dataList;


}
