package com.learning.base.util;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 * 
 * @version 1.0
 */
@Slf4j
public final class UUIDUtil {

    private static UIDFactory uuid = null;

    static {
        try {
            uuid = UIDFactory.getInstance("UUID");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Constructor for the UUIDGener object
     */
    private UUIDUtil() {
    }

    /**
     * 获取uuid字符
     * 
     * @author lihe 2013-7-4 下午5:31:09
     * @return
     * @see
     * @since
     */
    public static String getUUID() {
        return uuid.getNextUID();
    }
}
