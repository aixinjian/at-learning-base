package com.learning.base.util;


public class StrUtil {

    /**
     * 模拟mysql里面的函数，填充数据到对应的位数
     * @param str 原数据
     * @param paddedLength 填充到的目标位数
     * @param padString 填充数据
     * @return
     */
    public static String lpad(String str, int paddedLength, Object padString) {
        if (str == null || "".equals(str) || padString == null || "".equals(padString)) {
            return str;
        }

        String n_str=str;
        while (n_str.length() < paddedLength){
            n_str = padString + n_str;
        }
        return n_str;
    }

}

