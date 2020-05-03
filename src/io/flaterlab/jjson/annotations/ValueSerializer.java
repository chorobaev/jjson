package io.flaterlab.jjson.annotations;

public interface ValueSerializer<T> {
    Object toJsonValue(T value);

    T fromJsonValue(Object object);
}
