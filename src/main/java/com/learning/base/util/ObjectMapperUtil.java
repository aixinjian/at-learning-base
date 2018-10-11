package com.learning.base.util;

import com.learning.base.exception.BaseRuntimeException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


/**
 * Created by caowei on 16/4/26.
 */
public class ObjectMapperUtil {
    protected static ObjectMapper mapper = new ObjectMapper();
    static {

        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,false);

    }

    /**
     * 把json字符串反序列化为指定T类型的java类
     *
     * @param content
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (Exception e) {
            throw new BaseRuntimeException("转换为json出错,内容如下：" + content);
        }
    }

    /**
     * 把json字符串反序列化为指定TypeReference类型，支持List、嵌套javabean等复杂类型
     *
     * @param content
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T readValue(String content, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(content, typeReference);
        } catch (Exception e) {
            throw new BaseRuntimeException("转换为json出错,内容如下：" + content);
        }
    }

    /**
     * 把java对象序列化成json字符串
     *
     * @param value
     * @return
     */
    public static String writeValueAsString(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new BaseRuntimeException("转换为字符串出错");
        }
    }
}
