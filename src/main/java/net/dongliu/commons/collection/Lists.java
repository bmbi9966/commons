package net.dongliu.commons.collection;


import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

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
     */
    public static <T> List<T> of(T v1, T v2) {
        return unmodifiableList(Arrays.asList(v1, v2));
    }

    /**
     * Create new immutable List.
     */
    public static <T> List<T> of(T v1, T v2, T v3) {
        return unmodifiableList(Arrays.asList(v1, v2, v3));
    }

    /**
     * Create new immutable List.
     */
    public static <T> List<T> of(T v1, T v2, T v3, T v4) {
        return unmodifiableList(Arrays.asList(v1, v2, v3, v4));
    }

    /**
     * Create new immutable List.
     */
    public static <T> List<T> of(T v1, T v2, T v3, T v4, T v5) {
        return unmodifiableList(Arrays.asList(v1, v2, v3, v4, v5));
    }

    /**
     * Create new immutable List.
     */
    public static <T> List<T> of(T v1, T v2, T v3, T v4, T v5, T v6) {
        return unmodifiableList(Arrays.asList(v1, v2, v3, v4, v5, v6));
    }

    /**
     * Create new immutable List.
     * This method will do defensive copy for the param values.
     */
    @SafeVarargs
    public static <T> List<T> of(T... values) {
        return unmodifiableList(Arrays.asList(Arrays.copyOf(values, values.length)));
    }

    /**
     * Convert origin list to new List.
     *
     * @return list contains the result.
     */
    public static <S, T> List<T> convertTo(List<S> list, Function<S, T> function) {
        requireNonNull(list);
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
        requireNonNull(list);
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
        requireNonNull(list);
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
     * @param list can not be null
     * @return two list
     */
    public static <T> Pair<List<T>, List<T>> partition(List<T> list, Predicate<T> predicate) {
        requireNonNull(list);
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

    /**
     * Fetch the first element of list.
     * The element should not be null, or NullPointerException will be thrown.
     *
     * @param list can not be null
     * @return Optional
     */
    public static <T> Optional<T> first(List<T> list) {
        requireNonNull(list);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    /**
     * Fetch the first element of list.
     *
     * @param list can not be null
     * @return The first element. If list is empty, return null.
     */
    @Nullable
    public static <T> T firstOrNull(List<T> list) {
        requireNonNull(list);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Fetch the first element accepted by predicate in list.
     * The element should not be null, or NullPointerException will be thrown.
     *
     * @param list can not be null
     * @return Optional
     */
    public static <T> Optional<T> find(List<T> list, Predicate<T> predicate) {
        requireNonNull(list);
        for (T e : list) {
            if (predicate.test(e)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * Fetch the first element accepted by predicate in list.
     *
     * @param list can not be null
     * @return The first accepted element. If list is empty, return null.
     */
    @Nullable
    public static <T> T findOrNull(List<T> list, Predicate<T> predicate) {
        requireNonNull(list);
        for (T e : list) {
            if (predicate.test(e)) {
                return e;
            }
        }
        return null;
    }
}
