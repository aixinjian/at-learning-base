package com.learning.base.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class ConvertUtil {
    //List转成另外一种list
    public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toList());
    }

    //数组转成另外一种数组
    public static <T, U> U[] convertArray(T[] from,
                                          Function<T, U> func,
                                          IntFunction<U[]> generator) {
        return Arrays.stream(from).map(func).toArray(generator);
    }
}
