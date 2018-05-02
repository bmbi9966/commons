package net.dongliu.commons.collection;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 * Utils method for List
 */
public class Lists {

    private static final int INIT_SIZE = 16;

    /**
     * If list is null, return immutable empty list; else return list self.
     *
     * @param list the list
     * @param <T>  the element type
     * @return non-null list
     */
    @NotNull
    public static <T> List<T> nullToEmpty(@Nullable List<T> list) {
        if (list == null) {
            return of();
        }
        return list;
    }

    /**
     * Create new immutable empty List
     */
    public static <T> List<T> of() {
        return Collections.emptyList();
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v) {
        return Collections.singletonList(requireNonNull(v));
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v1, T v2) {
        return unmodifiableList(Arrays.asList(requireNonNull(v1), requireNonNull(v2)));
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v1, T v2, T v3) {
        return unmodifiableList(Arrays.asList(requireNonNull(v1), requireNonNull(v2), requireNonNull(v3)));
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v1, T v2, T v3, T v4) {
        return unmodifiableList(Arrays.asList(requireNonNull(v1),
                requireNonNull(v2),
                requireNonNull(v3),
                requireNonNull(v4)));
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v1, T v2, T v3, T v4, T v5) {
        return unmodifiableList(Arrays.asList(requireNonNull(v1),
                requireNonNull(v2),
                requireNonNull(v3),
                requireNonNull(v4),
                requireNonNull(v5)));
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v1, T v2, T v3, T v4, T v5, T v6) {
        return unmodifiableList(Arrays.asList(requireNonNull(v1),
                requireNonNull(v2),
                requireNonNull(v3),
                requireNonNull(v4),
                requireNonNull(v5),
                requireNonNull(v6)));
    }

    /**
     * Create new immutable List. Values cannot be null.
     * This method will do defensive copy for the param values.
     */
    @SafeVarargs
    public static <T> List<T> of(T... values) {
        for (T value : values) {
            requireNonNull(value);
        }
        return unmodifiableList(Arrays.asList(Arrays.copyOf(values, values.length)));
    }

    /**
     * Return a new immutable list, which contains the same elements as original list.
     *
     * @param list the original list
     * @param <T>  the element type
     * @return new immutable list
     */
    public static <T> List<T> copy(List<T> list) {
        requireNonNull(list);
        if (list.isEmpty()) {
            return of();
        }
        List<T> newList = new ArrayList<>(list);
        return unmodifiableList(newList);
    }

    /**
     * Convert origin list to new List.
     *
     * @return list contains the result.
     */
    public static <S, T> List<T> convertTo(List<S> list, Function<S, T> function) {
        return Collections2.convertToList(list, function);
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
     * Concat two list, to one new list.
     *
     * @param list1 list1
     * @param list2 list 2
     * @param <T>   element type
     * @return new List
     */
    public static <T> List<T> concat(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<>(list1.size() + list2.size());
        list.addAll(list1);
        list.addAll(list2);
        return list;
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
        return Iterables.find(list, predicate);
    }

    /**
     * Fetch the first element accepted by predicate in list.
     *
     * @param list can not be null
     * @return The first accepted element. If list is empty, return null.
     */
    @Nullable
    public static <T> T findOrNull(List<T> list, Predicate<T> predicate) {
        return Iterables.findOrNull(list, predicate);
    }

    /**
     * Convert list to array. This method using method reference, work better than the API in Collection. Call this method as:
     * <pre>
     *     List&lt;String&gt; list = Lists.of("1", "2", "3");
     *     String[] array = Lists.toArray(list, String[]::new);
     * </pre>
     *
     * @param list  the list
     * @param maker create the target array
     * @param <T>   element type
     * @return the target array containing elements in collection
     */
    public static <T> T[] toArray(List<T> list, IntFunction<T[]> maker) {
        return Collections2.toArray(list, maker);
    }
}
