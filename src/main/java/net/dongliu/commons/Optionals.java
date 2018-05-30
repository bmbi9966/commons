package net.dongliu.commons;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Utils method for Optional
 */
public class Optionals {

    /**
     * Convert Optional to Stream.
     * Note: Java9 has stream method in Optional already.
     */
    public static <T> Stream<T> stream(Optional<T> optional) {
        if (optional.isPresent()) {
            return Stream.of(optional.get());
        }
        return Stream.empty();
    }

    /**
     * If optional1 is present, return a optional with value of optional1; else return optional which equals optional2.
     *
     * @param optional1 optional1
     * @param optional2 optional2
     * @param <T>       value type
     * @return optional
     */
    public static <T> Optional<T> or(Optional<T> optional1, Optional<T> optional2) {
        if (optional1.isPresent()) {
            return optional1;
        }
        return optional2;
    }

    /**
     * If optional is present, return a optional with value of optional1; else, return optional created by supplier.
     * Note: Java9 has an or method in Optional already.
     *
     * @param optional optional1
     * @param supplier supply backup optional
     * @param <T>      value type
     * @return optional
     */
    public static <T> Optional<T> or(Optional<T> optional, Supplier<Optional<T>> supplier) {
        if (optional.isPresent()) {
            return optional;
        }
        return supplier.get();
    }
}
