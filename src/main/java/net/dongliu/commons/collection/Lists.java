package net.dongliu.commons.collection;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utils method for List
 */
public class Lists {

    private static final int INIT_SIZE = 16;

    /**
     * Create new immutable empty List
     */
    public static <T> List<T> of() {
        return Collections.emptyList();
    }

    /**
     * Create new immutable List
     */
    public static <T> List<T> of(T v) {
        return Collections.singletonList(v);
    }

    /**
     * Create new immutable List.
     * This method will do defensive copy for the param values.
     */
    @SafeVarargs
    public static <T> List<T> of(T... values) {
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(values, values.length)));
    }

    /**
     * Create a new ArrayList
     */
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * Create a new ArrayList
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... values) {
        ArrayList<T> list = new ArrayList<>(Math.max(INIT_SIZE, values.length));
        Collections.addAll(list, values);
        return list;
    }

    /**
     * Convert origin list to new List.
     *
     * @return list contains the result.
     */
    public static <S, T> List<T> convertTo(List<S> list, Function<S, T> function) {
        Objects.requireNonNull(list);
        List<T> newList = new ArrayList<>(list.size());
        for (S e : list) {
            newList.add(function.apply(e));
        }
        return newList;
    }

    /**
     * Filter list
     *
     * @return List which contains the elements in origin list, and accepted by predicate
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        Objects.requireNonNull(list);
        List<T> newList = new ArrayList<>(Math.min(INIT_SIZE, list.size()));
        for (T e : list) {
            if (predicate.test(e)) {
                newList.add(e);
            }
        }
        return newList;
    }

    /**
     * Split list, into multi subLists, each subList has the specified subSize, except the last one.
     */
    public static <T> List<List<T>> split(List<T> list, int subSize) {
        Objects.requireNonNull(list);
        if (subSize <= 0) {
            throw new IllegalArgumentException("SubList size must large than 0, but got: " + subSize);
        }

        int size = list.size();
        int count = (size - 1) / subSize + 1;
        List<List<T>> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(list.subList(i * subSize, Math.min(size, (i + 1) * subSize)));
        }
        return result;
    }

    /**
     * Divide list to two list, the first list contains elements accepted by predicate, the other contains other elements.
     *
     * @param list
     * @param predicate
     * @param <T>
     * @return two list
     */
    public static <T> Pair<List<T>, List<T>> partition(List<T> list, Predicate<T> predicate) {
        Objects.requireNonNull(list);
        List<T> list1 = new ArrayList<>(Math.min(INIT_SIZE, list.size()));
        List<T> list2 = new ArrayList<>(Math.min(INIT_SIZE, list.size()));
        for (T e : list) {
            if (predicate.test(e)) {
                list1.add(e);
            } else {
                list2.add(e);
            }
        }
        return Pair.of(list1, list2);
    }
}
