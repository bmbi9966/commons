package net.dongliu.commons;

import java.util.function.Function;
import java.util.function.IntFunction;

import static java.util.Objects.requireNonNull;

/**
 * Utils for Array.
 */
public class Arrays2 {

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
    public static <S, T> T[] convert(S[] array, IntFunction<T[]> maker, Function<S, T> function) {
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
    public static <T> T[] concat(IntFunction<T[]> maker, T[] array1, T[] array2) {
        requireNonNull(array1);
        requireNonNull(array2);
        T[] array = maker.apply(array1.length + array2.length);
        System.arraycopy(array1, 0, array, 0, array1.length);
        System.arraycopy(array2, 0, array, array1.length, array1.length);
        return array;
    }
}
