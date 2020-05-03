package io.flaterlab.jjson.serialization;

import io.flaterlab.jjson.Callback;
import io.flaterlab.jjson.annotations.JsonExclude;
import io.flaterlab.jjson.annotations.JsonName;
import io.flaterlab.jjson.annotations.JsonSerializer;
import io.flaterlab.jjson.annotations.ValueSerializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Serializer {

    public static String serialize(Object object) {
        StringBuilder sb = new StringBuilder();

        serializeObject(sb, object);

        return sb.toString();
    }

    private static void serializeObject(StringBuilder sb, Object obj) {
        Field[] fds = obj.getClass().getDeclaredFields();
        List<Field> fields = new ArrayList<>();

        for (Field f : fds) {
            if (f.getAnnotation(JsonExclude.class) == null) fields.add(f);
        }

        joinIterable(fields, sb, "{", "}", filed -> {
            serializeField(sb, filed, obj);
        });
    }

    private static <T> void joinIterable(
        Iterable<T> ths,
        StringBuilder sb,
        CharSequence prefix,
        CharSequence postfix,
        Callback<T> callback
    ) {
        sb.append(prefix);
        int count = 0;

        for (T element : ths) {
            if (++count > 1) sb.append(",");
            callback.call(element);
        }
        sb.append(postfix);
    }

    private static void serializeField(StringBuilder sb, Field field, Object obj) {
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

    private static ValueSerializer<Object> getSerializer(Field field) {
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

    private static void serializeFieldValue(StringBuilder sb, Object value) {
        if (value == null) sb.append("null");
        else if (value instanceof String) serializeString(sb, (String) value);
        else if (value instanceof Number || value instanceof Boolean) sb.append(value.toString());
        else if (value instanceof List<?>) serializeList(sb, (List<Object>) value);
        else serializeObject(sb, value);
    }

    private static void serializeList(StringBuilder sb, List<Object> list) {
        joinIterable(list, sb, "[", "]", data -> {
            serializeFieldValue(sb, data);
        });
    }

    private static void serializeString(StringBuilder sb, String s) {
        sb.append('\"');
        for (int i = 0; i < s.length(); i++) {
            sb.append(escape(s.charAt(i)));
        }
        sb.append('\"');
    }

    private static Object escape(char ch) {
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
