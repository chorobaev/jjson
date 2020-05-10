package io.flaterlab.jjson.examples;

import io.flaterlab.jjson.annotations.ValueSerializer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MySerializer implements ValueSerializer<List<String>> {

    @Override
    public Object toJsonValue(List<String> value) {
        if (value.size() == 0) return "Not specified";
        StringBuilder sb = new StringBuilder();
        final AtomicBoolean start = new AtomicBoolean(true);
        value.forEach(val -> {
            if (!start.get()) sb.append(" & ");
            start.set(false);
            sb.append(val);
        });
        return sb.toString();
    }

    @Override
    public List<String> fromJsonValue(Object object) {
        return null;
    }
}