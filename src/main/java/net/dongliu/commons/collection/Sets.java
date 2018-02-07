package net.dongliu.commons.collection;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Util methods for Set
 */
public class Sets {

    private static final int INIT_CAPACITY = 16;

    private static final float LOAD_FACTOR = 0.75f;

    /**
     * Create new immutable empty Set
     */
    public static <T> Set<T> of() {
        return Collections.emptySet();
    }

    /**
     * Create new immutable Set.
     * This method will do defensive copy for the param values.
     */
    @SafeVarargs
    public static <T> Set<T> of(T... values) {
        return Collections.unmodifiableSet(newHashSet(values));
    }

    /**
     * Create a new HashSet
     */
    public static <T> HashSet<T> newHashSet() {
        return new HashSet<>();
    }

    /**
     * Create a new HashSet
     */
    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... values) {
        return new HashSet<>(Arrays.asList(values));
    }

    /**
     * Convert origin set to new set.
     *
     * @return Set contains the result.
     */
    public static <S, T> Set<T> convertTo(Set<S> set, Function<S, T> function) {
        Objects.requireNonNull(set);
        Set<T> newSet = new HashSet<>(calculateCapacity(set.size()));
        for (S e : set) {
            newSet.add(function.apply(e));
        }
        return newSet;
    }

    /**
     * Filter set
     *
     * @return Set which contains the elements in origin set, and accepted by predicate
     */
    public static <T> Set<T> filter(Set<T> set, Predicate<T> predicate) {
        Objects.requireNonNull(set);
        Set<T> newSet = new HashSet<>(Math.min(calculateCapacity(set.size()), INIT_CAPACITY));
        for (T e : set) {
            if (predicate.test(e)) {
                newSet.add(e);
            }
        }
        return newSet;
    }

    private static int calculateCapacity(int size) {
        return (int) (size / LOAD_FACTOR) + 1;
    }
}
