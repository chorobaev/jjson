package io.flaterlab.jjson.examples;

import io.flaterlab.jjson.JJson;

import java.util.Arrays;

public class Example {

    public static void main(String[] args) {
        Book book = new Book("Harry Potter", 214, Arrays.asList("Adam", "Tom"), "12345A");
        Address address = new Address("Kyrgyzstan", "Bishkek", "Ankara", "1/8");
        book.setAddress(address);

        System.out.println(JJson.serialize(book));
    }
}
