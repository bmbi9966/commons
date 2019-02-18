package net.dongliu.commons;

import net.dongliu.commons.annotation.NonNull;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Utils for Array.
 */
public class Arrays2 {

    /**
     * Used to create a new array, with values. If a array is passed in, will return itself
     *
     * @param values the values.
     * @param <T>    the element type
     * @return array
     */
    @SafeVarargs
    public static <T> T[] of(T... values) {
        return values;
    }

    /**
     * Find the first element accepted by predicate in array.
     * The element should not be null, or NullPointerException will be thrown.
     *
     * @param array array and it's elements can not be null
     * @return Optional
     */
    public static <T> Optional<T> find(@NonNull T @NonNull [] array, Predicate<? super T> predicate) {
        for (T value : array) {
            if (predicate.test(value)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    /**
     * Find the first element index accepted by predicate in array.
     *
     * @param array the array
     * @return Optional
     */
    public static <T> OptionalInt findIndex(T[] array, Predicate<? super T> predicate) {
        return findIndex(array, 0, predicate);
    }


    /**
     * Find the first element index accepted by predicate in array.
     *
     * @param from  the index from which to find, included.
     * @param array the array
     * @return Optional
     */
    public static <T> OptionalInt findIndex(T[] array, int from, Predicate<? super T> predicate) {
        from = Math.max(0, from);
        for (int i = from; i < array.length; i++) {
            if (predicate.test(array[i])) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }


    /**
     * Find the last element accepted by predicate in array.
     * The element should not be null, or NullPointerException will be thrown.
     *
     * @param array array and it's elements can not be null
     * @return Optional
     */
    public static <T> Optional<T> reverseFind(@NonNull T @NonNull [] array, Predicate<? super T> predicate) {
        for (int i = array.length - 1; i >= 0; i--) {
            T value = array[i];
            if (predicate.test(value)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    /**
     * Find the last element index accepted by predicate in array.
     *
     * @param array the array
     * @return Optional
     */
    public static <T> OptionalInt reverseFindIndex(T[] array, Predicate<? super T> predicate) {
        return reverseFindIndex(array, array.length - 1, predicate);
    }

    /**
     * Find the last element index accepted by predicate in array.
     *
     * @param from  the index from which to find, included.
     * @param array the array
     * @return Optional
     */
    public static <T> OptionalInt reverseFindIndex(T[] array, int from, Predicate<? super T> predicate) {
        from = Math.min(array.length - 1, from);
        for (int i = from; i >= 0; i--) {
            if (predicate.test(array[i])) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    /**
     * Convert source array to target array. This method is used as:
     * <pre>
     *     String[] array = {"1", "2", "4"};
     *     Integer[] target = Arrays2.convert(array, Integer[]::new, Integer::valueOf);
     * </pre>
     *
     * @param array    the source array
     * @param maker    create the target array.
     * @param function convert source array element to target array element
     * @param <S>      source array element type
     * @param <T>      target array element type
     * @return the target array
     */
    public static <S, T> T[] convert(S[] array, IntFunction<T[]> maker, Function<? super S, ? extends T> function) {
        requireNonNull(array);
        T[] target = maker.apply(array.length);
        for (int i = 0; i < array.length; i++) {
            target[i] = function.apply(array[i]);
        }
        return target;
    }

    /**
     * Concat two array to new array. This method is used as:
     * <pre>
     *     String[] array1 = {"1", "2", "4"};
     *     String[] array2 = {"4", "6", "8"};
     *     String[] array = Arrays2.concat(String[]::new, array1, array2);
     * </pre>
     *
     * @param maker create the target array.
     * @param <T>   array element type
     * @return the target array
     */
    @SafeVarargs
    public static <T> T[] concat(IntFunction<T[]> maker, T[] array1, T... array2) {
        requireNonNull(array1);
        requireNonNull(array2);
        T[] array = maker.apply(array1.length + array2.length);
        System.arraycopy(array1, 0, array, 0, array1.length);
        System.arraycopy(array2, 0, array, array1.length, array1.length);
        return array;
    }

}
