package com.learning.base.util;

import com.learning.base.exception.BaseRuntimeException;
import com.learning.base.response.PageResponseResult;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CopyUtil {
    /**
     * bean转为另一个bean
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T transfer(Object source, Class<T> targetClass) {
        if (source == null){
            return null;
        }
        try {
            T t = targetClass.newInstance();
            BeanUtils.copyProperties(source,t);
            return t;
        } catch (InstantiationException e) {
            throw new BaseRuntimeException("拷贝bean有误：" + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new BaseRuntimeException("拷贝bean有误：" + e.getMessage(), e);
        }
    }


    /**
     * 泛型为一种bean的list转为另一种泛型bean的list
     *
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> transfer(List<?> sourceList, Class<T> targetClass){
        if (sourceList == null){
            return null;
        }
        return sourceList.stream().map((source)->transfer(source,targetClass)).collect(Collectors.toList());
    }

    /**
     * 泛型为一种bean的PageResponseResult转为另一种泛型bean的PageResponseResult
     *
     * @param sourcePageResponseResult
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> PageResponseResult<T> transfer(PageResponseResult<?> sourcePageResponseResult, Class<T> targetClass){
        if (sourcePageResponseResult == null){
            return null;
        }
        List<T> targetList = transfer(sourcePageResponseResult.getDataList(), targetClass);
        return new PageResponseResult<>(sourcePageResponseResult.getTotalCount(), targetList);
    }

//    /**
//     * map转换为bean
//     *
//     * @param map
//     * @param targetClass
//     * @param <T>
//     * @return
//     */
//    public static <T> T mapTransferBean(Map map, Class<T> targetClass){
//        if (map == null){
//            return null;
//        }
//        try {
//            T t = targetClass.newInstance();
//            org.apache.commons.beanutils.BeanUtils.populate(t, map);
//            return t;
//        } catch (InstantiationException e) {
//            throw new BaseRuntimeException("map转换为bean：" + e.getMessage(), e);
//        } catch (IllegalAccessException e) {
//            throw new BaseRuntimeException("map转换为bean：" + e.getMessage(), e);
//        } catch (InvocationTargetException e) {
//            throw new BaseRuntimeException("map转换为bean：" + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * bean转换为map
//     * @param bean
//     * @return
//     */
//    public static Map<String, String> beanTransferMap(Object bean){
//        try {
//            Map<String, String> map = org.apache.commons.beanutils.BeanUtils.describe(bean);
//            return map;
//        } catch (IllegalAccessException e) {
//            throw new BaseRuntimeException("bean转换为map：" + e.getMessage(), e);
//        } catch (InvocationTargetException e) {
//            throw new BaseRuntimeException("bean转换为map：" + e.getMessage(), e);
//        } catch (Exception e) {
//            throw new BaseRuntimeException("bean转换为map：" + e.getMessage(), e);
//        }
//    }

}
