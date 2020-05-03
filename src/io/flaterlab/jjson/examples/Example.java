package io.flaterlab.jjson.examples;

import io.flaterlab.jjson.JJson;

import java.util.Arrays;

public class Example {

    public static void main(String[] args) {
        Book book = new Book("Harry Potter", 14, Arrays.asList("Adam", "Tom"));
        System.out.println(JJson.toJson(book));
    }
}
