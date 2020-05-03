package io.flaterlab.jjson.serialization;

import io.flaterlab.jjson.Utility;
import io.flaterlab.jjson.annotations.JsonExclude;
import io.flaterlab.jjson.annotations.JsonName;
import io.flaterlab.jjson.annotations.JsonSerializer;
import io.flaterlab.jjson.annotations.ValueSerializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Serializer {

    public String serialize(Object object) {
        StringBuilder sb = new StringBuilder();

        serializeObject(sb, object);

        return sb.toString();
    }

    private void serializeObject(StringBuilder sb, Object obj) {
        Field[] fds = obj.getClass().getDeclaredFields();
        List<Field> fields = new ArrayList<>();

        for (Field f : fds) {
            if (f.getAnnotation(JsonExclude.class) == null) fields.add(f);
        }

        Utility.joinIterable(fields, sb, "{", "}", filed -> {
            serializeField(sb, filed, obj);
        });
    }

    private void serializeField(StringBuilder sb, Field field, Object obj) {
        if (Modifier.isPrivate(field.getModifiers())) {
            field.setAccessible(true);
        }

        JsonName jsonName = field.getAnnotation(JsonName.class);
        String fieldName = jsonName == null ? field.getName() : jsonName.name();

        serializeString(sb, fieldName);
        sb.append(":");

        Object value = null;
        try {
            ValueSerializer<Object> serializer = getSerializer(field);
            value = field.get(obj);

            if (serializer != null) {
                value = serializer.toJsonValue(value);
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        serializeFieldValue(sb, value);
    }

    private ValueSerializer<Object> getSerializer(Field field) {
        JsonSerializer JsonSerializerAnn = field.getAnnotation(JsonSerializer.class);
        if (JsonSerializerAnn == null) return null;

        Class serializerClass = JsonSerializerAnn.serializerClass();

        try {
            Object serializer = serializerClass.newInstance();
            return (ValueSerializer<Object>) serializer;
        } catch (Exception ex) {
            return null;
        }
    }

    private void serializeFieldValue(StringBuilder sb, Object value) {
        if (value == null) sb.append("null");
        else if (value instanceof String) serializeString(sb, (String) value);
        else if (value instanceof Number || value instanceof Boolean) sb.append(value.toString());
        else if (value instanceof List<?>) serializeList(sb, (List<Object>) value);
        else serializeObject(sb, value);
    }

    private void serializeList(StringBuilder sb, List<Object> list) {
        Utility.joinIterable(list, sb, "[", "]", data -> {
            serializeFieldValue(sb, data);
        });
    }

    private void serializeString(StringBuilder sb, String s) {
        sb.append('\"');
        for (int i = 0; i < s.length(); i++) {
            sb.append(escape(s.charAt(i)));
        }
        sb.append('\"');
    }

    private Object escape(char ch) {
        if (ch == '\\') return "\\\\";
        if (ch == '\"') return "\\\"";
        if (ch == '\b') return "\\b";
        if (ch == '\u000C') return "\\f";
        if (ch == '\n') return "\\n";
        if (ch == '\r') return "\\r";
        if (ch == '\t') return "\\t";
        return ch;
    }
}
