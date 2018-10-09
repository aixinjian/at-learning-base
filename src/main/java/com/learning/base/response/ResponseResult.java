package com.learning.base.response;

import com.learning.base.common.BaseSerializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> extends BaseSerializable {
    /**
     * 状态: ok 成功, fail 失败
     */
    @ApiModelProperty("状态: ok 成功, fail 失败")
    private String result;
    /**
     * 状态码: 200 成功, 201 失败, 202 需要登陆
     */
    @ApiModelProperty("状态码: 200 成功, 201 失败, 202 需要登陆")
    private Integer  rescode;
    /**
     * 备注原因
     */
    @ApiModelProperty("备注原因")
    private String msg;
    /**
     * 返回对象
     */
    @ApiModelProperty("返回数据对象")
    private T data;
}
