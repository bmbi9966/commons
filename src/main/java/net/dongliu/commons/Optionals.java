package net.dongliu.commons;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.*;
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

    /**
     * Map optional value to optional long.
     *
     * @param optional the optional value
     * @param function the function map value to long
     * @param <T>      the value type
     * @return OptionalLong
     */
    public static <T> OptionalLong mapToLong(Optional<T> optional, ToLongFunction<? super T> function) {
        if (optional.isPresent()) {
            return OptionalLong.of(function.applyAsLong(optional.get()));
        }
        return OptionalLong.empty();
    }

    /**
     * Map optional int to optional long.
     *
     * @param optional the optional int
     * @param function the function map int to long
     * @return OptionalLong
     */
    public static OptionalLong mapToLong(OptionalInt optional, IntToLongFunction function) {
        if (optional.isPresent()) {
            return OptionalLong.of(function.applyAsLong(optional.getAsInt()));
        }
        return OptionalLong.empty();
    }

    /**
     * Map optional double to optional long.
     *
     * @param optional the optional double
     * @param function the function map double to long
     * @return OptionalLong
     */
    public static OptionalLong mapToLong(OptionalDouble optional, DoubleToLongFunction function) {
        if (optional.isPresent()) {
            return OptionalLong.of(function.applyAsLong(optional.getAsDouble()));
        }
        return OptionalLong.empty();
    }

    /**
     * Map optional value to optional int.
     *
     * @param optional the optional value
     * @param function the function map value to int
     * @param <T>      the value type
     * @return OptionalInt
     */
    public static <T> OptionalInt mapToInt(Optional<T> optional, ToIntFunction<? super T> function) {
        if (optional.isPresent()) {
            return OptionalInt.of(function.applyAsInt(optional.get()));
        }
        return OptionalInt.empty();
    }

    /**
     * Map optional long to optional int.
     *
     * @param optional the optional long
     * @param function the function map long to int
     * @return OptionalLong
     */
    public static OptionalInt mapToInt(OptionalLong optional, LongToIntFunction function) {
        if (optional.isPresent()) {
            return OptionalInt.of(function.applyAsInt(optional.getAsLong()));
        }
        return OptionalInt.empty();
    }

    /**
     * Map optional double to optional int.
     *
     * @param optional the optional double
     * @param function the function map double to int
     * @return OptionalLong
     */
    public static OptionalInt mapToInt(OptionalDouble optional, DoubleToIntFunction function) {
        if (optional.isPresent()) {
            return OptionalInt.of(function.applyAsInt(optional.getAsDouble()));
        }
        return OptionalInt.empty();
    }

    /**
     * Map optional value to optional double.
     *
     * @param optional the optional value
     * @param function the function map value to double
     * @param <T>      the value type
     * @return OptionalInt
     */
    public static <T> OptionalDouble mapToDouble(Optional<T> optional, ToDoubleFunction<? super T> function) {
        if (optional.isPresent()) {
            return OptionalDouble.of(function.applyAsDouble(optional.get()));
        }
        return OptionalDouble.empty();
    }

    /**
     * Map optional long to optional double.
     *
     * @param optional the optional long
     * @param function the function map long to double
     * @return OptionalLong
     */
    public static OptionalDouble mapToInt(OptionalLong optional, LongToDoubleFunction function) {
        if (optional.isPresent()) {
            return OptionalDouble.of(function.applyAsDouble(optional.getAsLong()));
        }
        return OptionalDouble.empty();
    }

    /**
     * Map optional int to optional double.
     *
     * @param optional the optional int
     * @param function the function map int to double
     * @return OptionalLong
     */
    public static OptionalDouble mapToInt(OptionalInt optional, IntToDoubleFunction function) {
        if (optional.isPresent()) {
            return OptionalDouble.of(function.applyAsDouble(optional.getAsInt()));
        }
        return OptionalDouble.empty();
    }

    /**
     * Map optional long to optional value.
     *
     * @param optional the optional long
     * @param function the function map long to value
     * @param <T>      the value type
     * @return Optional Value
     */
    public static <T> Optional<T> mapTo(OptionalLong optional, LongFunction<? extends T> function) {
        if (optional.isPresent()) {
            return Optional.of(function.apply(optional.getAsLong()));
        }
        return Optional.empty();
    }

    /**
     * Map optional int to optional value.
     *
     * @param optional the optional int
     * @param function the function map int to value
     * @param <T>      the value type
     * @return Optional Value
     */
    public static <T> Optional<T> mapTo(OptionalInt optional, IntFunction<? extends T> function) {
        if (optional.isPresent()) {
            return Optional.of(function.apply(optional.getAsInt()));
        }
        return Optional.empty();
    }

    /**
     * Map optional double to optional value.
     *
     * @param optional the optional double
     * @param function the function map double to value
     * @param <T>      the value type
     * @return Optional Value
     */
    public static <T> Optional<T> mapTo(OptionalDouble optional, DoubleFunction<? extends T> function) {
        if (optional.isPresent()) {
            return Optional.of(function.apply(optional.getAsDouble()));
        }
        return Optional.empty();
    }
}
