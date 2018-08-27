package net.dongliu.commons.collection;


import net.dongliu.commons.annotation.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import static java.lang.Math.addExact;
import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static net.dongliu.commons.collection.Collections2.convertToList;
import static net.dongliu.commons.collection.Collections2.partitionToList;

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
        return emptyList();
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v) {
        return singletonList(requireNonNull(v));
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v1, T v2) {
        return unmodifiableList(asList(v1, v2));
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v1, T v2, T v3) {
        return unmodifiableList(asList(v1, v2, v3));
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v1, T v2, T v3, T v4) {
        return unmodifiableList(asList(v1, v2, v3, v4));
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v1, T v2, T v3, T v4, T v5) {
        return unmodifiableList(asList(v1, v2, v3, v4, v5));
    }

    /**
     * Create new immutable List. Values cannot be null.
     */
    public static <T> List<T> of(T v1, T v2, T v3, T v4, T v5, T v6) {
        return unmodifiableList(asList(v1, v2, v3, v4, v5, v6));
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
        return unmodifiableList(asList((T[]) values.clone()));
    }


    /**
     * Copy list, return a new immutable list, contains the elements in original list.
     * If original list is already ImmutableList, return its self.
     *
     * @param list the original list.
     * @param <T>  the element type
     * @return the immutable list
     */
    public static <T> List<T> copy(List<T> list) {
        requireNonNull(list);
        if (list.isEmpty()) {
            return Lists.of();
        }
        List<T> newList = new ArrayList<>(list.size());
        newList.addAll(list);
        return unmodifiableList(newList);
    }

    /**
     * Convert origin list to new List, the elements are converted by specific function.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @param function function to convert elements
     * @return list contains the result.
     */
    public static <S, T> List<T> convert(List<S> list, Function<? super S, ? extends T> function) {
        requireNonNull(list);
        return convertToList(list, function);
    }

    /**
     * Convert origin list to new List, the elements are converted by specific function.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @param function function to convert elements
     * @return list contains the result.
     * @deprecated use {@link #convert(List, Function)}
     */
    @Deprecated
    public static <S, T> List<T> convertTo(List<S> list, Function<? super S, ? extends T> function) {
        requireNonNull(list);
        return convertToList(list, function);
    }

    /**
     * Filter list, return a new list which contains the elements in origin list which accepted by predicate.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @return new list
     */
    public static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        requireNonNull(list);
        List<T> newList = new ArrayList<>(Math.min(INIT_SIZE, list.size()));
        for (T e : list) {
            if (predicate.test(e)) {
                newList.add(e);
            }
        }
        return unmodifiableList(newList);
    }


    /**
     * Return a new reversed List.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @param list the list
     * @param <T>  the element type
     * @return reversed List
     */
    public static <T> List<T> reverse(List<T> list) {
        requireNonNull(list);
        List<T> newList = new ArrayList<>(list);
        Collections.reverse(newList);
        return unmodifiableList(newList);
    }

    /**
     * Return a new sorted List, the element is compared by comparator.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @param list the list
     * @param <T>  the element type
     * @return sorted List
     */
    public static <T> List<T> sorted(List<T> list, Comparator<? super T> comparator) {
        requireNonNull(list);
        requireNonNull(comparator);
        List<T> newList = new ArrayList<>(list);
        newList.sort(comparator);
        return unmodifiableList(newList);
    }

    /**
     * Return a new sorted List.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @param list the list
     * @param <T>  the element type
     * @return sorted List
     */
    public static <T extends Comparable<T>> List<T> sorted(List<T> list) {
        return sorted(list, comparing(s -> s));
    }

    /**
     * Concat two lists, to one new list.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @param list1 list1
     * @param list2 list2
     * @param <T>   element type
     * @return new List
     */
    public static <T> List<T> concat(List<T> list1, List<T> list2) {
        requireNonNull(list1);
        requireNonNull(list2);
        int totalSize = addExact(list1.size(), list2.size());
        List<T> list = new ArrayList<>(totalSize);
        list.addAll(list1);
        list.addAll(list2);
        return unmodifiableList(list);
    }

    /**
     * Concat multi lists, to one new list.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @param list1      list1
     * @param list2      list2
     * @param otherLists other lists to concat
     * @param <T>        element type
     * @return new List
     */
    @SafeVarargs
    public static <T> List<T> concat(List<T> list1, List<T> list2, List<T>... otherLists) {
        requireNonNull(list1);
        requireNonNull(list2);
        requireNonNull(otherLists);
        int totalSize = addExact(list1.size(), list2.size());
        for (List<T> list : otherLists) {
            requireNonNull(list);
            totalSize = addExact(totalSize, list.size());
        }

        List<T> list = new ArrayList<>(totalSize);
        list.addAll(list1);
        list.addAll(list2);
        for (List<T> otherList : otherLists) {
            list.addAll(otherList);
        }
        return unmodifiableList(list);
    }

    /**
     * Split list, into multi subLists, each subList has the specified subSize, except the last one.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @return List of SubLists
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
        return unmodifiableList(result);
    }

    /**
     * Divide list to two list, the first list contains elements accepted by predicate,
     * the other contains other elements.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @param list can not be null
     * @return two list
     */
    public static <T> Pair<List<T>, List<T>> partition(List<T> list, Predicate<? super T> predicate) {
        requireNonNull(list);
        return partitionToList(list, predicate);
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
    public static <T> Optional<T> find(List<T> list, Predicate<? super T> predicate) {
        return Iterables.find(list, predicate);
    }

    /**
     * Fetch the first element accepted by predicate in list.
     *
     * @param list can not be null
     * @return The first accepted element. If list is empty, return null.
     */
    @Nullable
    public static <T> T findOrNull(List<T> list, Predicate<? super T> predicate) {
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
    public static <T> T[] toArray(List<? extends T> list, IntFunction<T[]> maker) {
        return Collections2.toArray(list, maker);
    }

    /**
     * Traverse on a list.
     *
     * @param list     the list
     * @param consumer the consumer
     * @param <T>      the data type
     */
    public static <T> void forEach(List<T> list, LastElementAwareConsumer<? super T> consumer) {
        requireNonNull(list);
        requireNonNull(consumer);
        Collections2.forEach(list, consumer);
    }
}
