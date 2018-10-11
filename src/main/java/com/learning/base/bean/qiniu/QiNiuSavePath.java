package com.learning.base.bean.qiniu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class QiNiuSavePath {

    /**
     * 地址
     */
    @Getter
    private String url;

    /**
     * 任务编号
     */
    @Getter
    private String persistentId;
}
