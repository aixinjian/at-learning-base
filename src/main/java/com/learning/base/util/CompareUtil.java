package com.learning.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompareUtil {

    /**
     * 保留差异，用新的bean返回
     *
     * @param target
     * @param origin
     * @param <T>
     * @return
     */
    public static <T> T retainDifferent(T target, T origin) {

        Map<String, Object> targetMap = ObjectMapperUtil.readValue(ObjectMapperUtil.writeValueAsString(target), Map.class);
        Map<String, Object> originMap = ObjectMapperUtil.readValue(ObjectMapperUtil.writeValueAsString(origin), Map.class);

        originMap.forEach((key, value) -> {
            Object targetValue = targetMap.get(key);
            if (isEquals(value, targetValue)) {
                targetMap.remove(key);
            }
        });

        if (targetMap.isEmpty()) {
            return null;
        }
        return ObjectMapperUtil.readValue(ObjectMapperUtil.writeValueAsString(targetMap), (Class<T>) target.getClass());
    }

    /**
     * 保留差异，用新的List返回
     *
     * @param targetList
     * @param originList
     * @param <T>
     * @return
     */
    public static <T> List<T> retainDifferent(List<T> targetList, List<T> originList) {
        List<T> result = new ArrayList<>();
        result.addAll(targetList);
        result.removeAll(originList);
        return result;
    }


    /**
     * 判断差异
     *
     * @param target
     * @param origin
     * @param <T>
     * @return
     */
    public static <T> boolean isEquals(T target, T origin) {
        if(target == null && origin == null)
            return true;
        if(target == null || origin == null)
            return false;
        return target.equals(origin);
    }
}
