package com.example.url_shortner.util;

public class Base62Encoder {

    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encode(long value) {

        StringBuilder result = new StringBuilder();

        while (value > 0) {
            result.append(BASE62.charAt((int) (value % 62)));
            value /= 62;

        }

        return result.reverse().toString();
    }
}
