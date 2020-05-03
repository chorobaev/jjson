package io.flaterlab.jjson;

import io.flaterlab.jjson.serialization.Serializer;

public class JJson {

    public static String serialize(Object object) {
        return Serializer.serialize(object);
    }

    public static <T> T deserialize(String json, Class<T> tClass) {
        throw new IllegalStateException("This functionality is not implemented!");
    }
}
