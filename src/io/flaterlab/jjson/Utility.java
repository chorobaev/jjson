package io.flaterlab.jjson;

public class Utility {

    private static final String separator = ",";

    public static <T> void joinIterable(
        Iterable<T> ths,
        StringBuilder sb,
        CharSequence prefix,
        CharSequence postfix,
        Callback<T> callback
    ) {
        sb.append(prefix);
        int count = 0;

        for (T element : ths) {
            if (++count > 1) sb.append(separator);
            callback.call(element);
        }
        sb.append(postfix);
    }
}
