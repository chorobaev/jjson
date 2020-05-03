package io.flaterlab.jjson.examples;

import io.flaterlab.jjson.annotations.JsonExclude;
import io.flaterlab.jjson.annotations.JsonName;
import io.flaterlab.jjson.annotations.JsonSerializer;
import io.flaterlab.jjson.annotations.ValueSerializer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Book {
    @JsonName(name = "title")
    private String name;

    private int pages;

    @JsonSerializer(serializerClass = MySerializer.class)
    private List<String> authors;

    @JsonExclude
    private int excludedValue;

    public Book(String name, int pages, List<String> authors) {
        this.name = name;
        this.pages = pages;
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public int getExcludedValue() {
        return excludedValue;
    }

    public void setExcludedValue(int excludedValue) {
        this.excludedValue = excludedValue;
    }

    public static class MySerializer implements ValueSerializer<List<String>> {

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
}
