package com.learning.base.util;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Created by caowei on 2017/9/12.
 */
public class MergeUtil {

    /**
     * 把sourceList里面的一些属性合并到targetList里面
     * 基于testFunction的条件,合入逻辑实现为biConsumer
     *
     * @param targetList
     * @param sourceList
     * @param testFunction
     * @param biConsumer
     * @param <T>
     * @param <S>
     */
    public static <T, S> void merge(List<T> targetList, List<S> sourceList, BiFunction<? super T, ? super S, Boolean> testFunction,
                                     BiConsumer<? super T, ? super S> biConsumer) {

        targetList.forEach((t)->{
            Optional<S> optional = sourceList.stream().filter(s->testFunction.apply(t, s)).findFirst();
            if (optional.isPresent()) {
                biConsumer.accept(t, optional.get());
            }
        });
    }
}
