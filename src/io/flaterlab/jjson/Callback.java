package io.flaterlab.jjson;

public interface Callback<T> {
    void call(T data);
}