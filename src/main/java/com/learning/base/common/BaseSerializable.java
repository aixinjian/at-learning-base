package com.learning.base.common;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public abstract class BaseSerializable implements Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
