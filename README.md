## Description
**JJson** is a simple JSON serialization/deserialization library for POJO 
(Plain Old Java Object) classes. Uses Java Reflection to analyse POJO classes at runtime. 
Provides basic configurations like specifying serialization names, excluding
properties from being serialized, and custom serializers for specific properties. 
Annotations are used to apply serialization options.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Version: v1.0.0](https://img.shields.io/badge/Version-v1.0.0-brightgreen)](https://opensource.org/licenses/MIT)


**Note:** This project has been created for educational purpose.

## Serialization/deserialization options
There three types of annotations.
* `@JsonName("name")` - uses the specified name for serialization. 
* `@JsonExclude` - prevents the field from being serialized.
* `@JsonSerializer(serializerClass = ValueSerialzer.class)` - expects 
an implementation of `ValueSerialzer` interface, and uses it to
serialize/deserialize specified field.

## Examples
Use `JJson.serialize(Object);` to serialize and 
`JJson.deserialize(String, Class<T>);` to deserialize 
(_deserialization is not implemented for now_).

Let's use the following `Book` POJO as an example.

```java
public class Book {
    @JsonName(name = "title")
    private String name;
    private int pages;
    @JsonSerializer(serializerClass = MySerializer.class)
    private List<String> authors;
    @JsonExclude
    private String ISBN;

    public Book(String name, int pages, List<String> authors) {
        this.name = name;
        this.pages = pages;
        this.authors = authors;
    }
    // Getters and setters
    // ...
}
```
`MySerializer`'s implementation is
```java
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
```

Create instance of the `Book`, and serialize it.
```java
    Book book = new Book("Harry Potter", 14, Arrays.asList("Adam", "Tom"), "12345A");
    String json = JJson.serialize(book);
    System.out.println(json);
```

It outputs as following.
```json
{"title":"Harry Potter","pages":14,"authors":"Adam & Tom"}
```

If we remove annotations from the `Book` class,
```java
public class Book {
    private String name;
    private int pages;
    private List<String> authors;
    private String ISBN;

    // Constructor, and getters/setters
    // ...
}
``` 
we will get this result
```json
{"name":"Harry Potter","pages":14,"authors":["Adam","Tom"],"ISBN":"12345A"}
```

Full example code can be found [here][1].

[1]: src/io/flaterlab/jjson/examples