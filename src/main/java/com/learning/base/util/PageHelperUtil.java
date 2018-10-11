package com.learning.base.util;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

public class PageHelperUtil {

    /**
     * 默认分页不查询count
     * @param pageNo
     * @param pageSize
     * @param <E>
     * @return
     */
    public static <E> Page<E> startPage(int pageNo, int pageSize) {
        return PageHelper.startPage(pageNo + 1, pageSize, false);
    }

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param count true 先查询count
     * @param <E>
     * @return
     */
    public static <E> Page<E> startPage(int pageNo, int pageSize, boolean count) {
        return PageHelper.startPage(pageNo + 1, pageSize, count);
    }
}
