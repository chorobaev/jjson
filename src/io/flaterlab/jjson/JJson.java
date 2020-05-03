package io.flaterlab.jjson;

import io.flaterlab.jjson.serialization.Serializer;

public class JJson {

    public static String toJson(Object object) {
        return new Serializer().serialize(object);
    }
}
