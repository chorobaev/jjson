package io.flaterlab.jjson.examples;

import io.flaterlab.jjson.annotations.JsonExclude;
import io.flaterlab.jjson.annotations.JsonName;
import io.flaterlab.jjson.annotations.JsonSerializer;

import java.util.List;

public class Book {
    @JsonName(name = "title")
    private String name;
    private int pages;
    @JsonSerializer(serializerClass = MySerializer.class)
    private List<String> authors;
    @JsonExclude
    private String ISBN;
    private Address address;

    public Book(String name, int pages, List<String> authors, String ISBN) {
        this.name = name;
        this.pages = pages;
        this.authors = authors;
        this.ISBN = ISBN;
    }

    public Book(String name, int pages, List<String> authors, String ISBN, Address address) {
        this(name, pages, authors, ISBN);
        this.address = address;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
