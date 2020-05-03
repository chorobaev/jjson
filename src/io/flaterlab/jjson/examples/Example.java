package io.flaterlab.jjson.examples;

import io.flaterlab.jjson.JJson;

import java.util.Arrays;

public class Example {

    public static void main(String[] args) {
        Book book = new Book("Harry Potter", 14, Arrays.asList("Adam", "Tom"), "12345A");
        System.out.println(JJson.serialize(book));
    }
}
