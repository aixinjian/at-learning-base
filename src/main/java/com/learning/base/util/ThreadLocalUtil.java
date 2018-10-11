package com.learning.base.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtil {

    /**
     * 添加入ThreadLocal里的map
     *
     * @param key
     * @param value
     * @param threadLocal
     * @param <T>
     */
    public static <T> void put(String key, T value, ThreadLocal<Map<String, T>> threadLocal) {
        Map<String, T> localCacheMap = threadLocal.get();
        if (localCacheMap == null) {
            localCacheMap = new HashMap<>();
            threadLocal.set(localCacheMap);
        }
        localCacheMap.put(key, value);
    }

    /**
     * 从ThreadLocal里的map中取值
     *
     * @param key
     * @param threadLocal
     * @param <T>
     * @return
     */
    public static <T> T get(String key, ThreadLocal<Map<String, T>> threadLocal) {
        Map<String, T> localCacheMap = threadLocal.get();
        T cache = null;
        if (localCacheMap != null) {
            cache = localCacheMap.get(key);
        }
        return cache;
    }
}
