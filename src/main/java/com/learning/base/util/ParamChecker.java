package com.learning.base.util;

import com.learning.base.exception.ArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * 客户端请求参数断言工具类
 * - 校验失败时, 抛出异常{@link ArgumentException}
 *
 * @author 易永健[of1972]
 * company qianmi.com
 * Date    2017-07-29
 */
public class ParamChecker {

    /**
     * 断言字符串空
     * 该断言会会判断字符串是否为空，只包含空白符也会判断为空
     *
     * @param string 断言字符串
     * @param msg    提示信息
     * @throws ArgumentException 异常
     */
    public static void isBlank(String string, String msg) {
        if (StringUtils.isNotBlank(string)) {
            throw new ArgumentException(msg);
        }
    }

    /**
     * 断言字符串非空
     * 该断言会会判断字符串是否为空，只包含空白符也会判断为空
     *
     * @param string 断言字符串
     * @param msg    提示信息
     * @throws ArgumentException 异常
     */
    public static void notBlank(String string, String msg) {
        if (StringUtils.isBlank(string)) {
            throw new ArgumentException(msg);
        }
    }

    /**
     * 断言集合非空
     *
     * @param collection 集合
     * @param msg        提示信息
     * @throws ArgumentException 异常
     */
    public static void notEmpty(Collection collection, String msg) {
        if (null == collection || collection.isEmpty()) {
            throw new ArgumentException(msg);
        }
    }

    /**
     * 断言String集合非空和集合元素非空
     *
     * @param collection String集合
     * @param elementMsg 集合元素不符合要求提示信息
     * @throws ArgumentException 异常
     */
    public static void nonBlankElements(Collection<String> collection, String elementMsg) {
        for (String str : collection) {
            notBlank(str, elementMsg);
        }
    }

    /**
     * 断言对象非null
     *
     * @param object 对象
     * @param msg    提示信息
     * @throws ArgumentException 异常
     */
    public static void nonNull(Object object, String msg) {
        if (null == object) {
            throw new ArgumentException(msg);
        }
    }

    /**
     * 断言对象null
     *
     * @param object 对象
     * @param msg    提示信息
     * @throws ArgumentException 异常
     */
    public static void isNull(Object object, String msg) {
        if (null != object) {
            throw new ArgumentException(msg);
        }
    }

    /**
     * 断言表达式结果为真,为假则提示异常信息
     *
     * @param boolExpression 布尔表达式
     * @param trueMsg        提示信息
     * @throws ArgumentException 异常
     */
    public static void expectTrue(boolean boolExpression, String trueMsg) {
        if (!boolExpression) {
            throw new ArgumentException(trueMsg);
        }
    }

    /**
     * 断言表达式结果为假,为真则提示异常信息
     *
     * @param boolExpression 布尔表达式
     * @param trueMsg        提示信息
     * @throws ArgumentException 异常
     */
    public static void expectFalse(boolean boolExpression, String trueMsg) {
        if (boolExpression) {
            throw new ArgumentException(trueMsg);
        }
    }

    /**
     * 断言表达式结果都为真，抛出提示信息
     *
     * @param booleans 布尔表达式数组
     * @param msg      提示信息
     * @throws ArgumentException 异常
     */
    public static void expectAnyFalse(String msg, Boolean... booleans) throws ArgumentException {
        if (Arrays.stream(booleans).allMatch(t -> t)) {
            throw new ArgumentException(msg);
        }
    }

    /**
     * 检查集合元素个数在[min, max]区间
     *
     * @param collection  表达式的参数
     * @param minElements 集合元素个数最小值
     * @param maxElements 集合元素个数最大值
     * @param outRangeMsg 条件表达式为true的提示消息
     */
    public static void expectInRange(Collection collection, int minElements, int maxElements, String outRangeMsg) {
        int elements = collection.size();
        if (elements < minElements || elements > maxElements) {
            throw new ArgumentException(outRangeMsg);
        }
    }

}
