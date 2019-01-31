package net.dongliu.commons.collection;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

/**
 * Util methods for Set
 */
public class Sets {

    private static final int INIT_CAPACITY = 16;

    private static final float LOAD_FACTOR = 0.75f;

    /**
     * If set is null, return immutable empty set; else return set self.
     *
     * @param set the set
     * @param <T> the element type
     * @return non-null set
     */
    public static <T> Set<T> nullToEmpty(@Nullable Set<T> set) {
        if (set == null) {
            return of();
        }
        return set;
    }

    /**
     * Create new immutable empty Set
     */
    public static <T> Set<T> of() {
        return Collections.emptySet();
    }

    /**
     * Create new immutable empty Set. Values cannot be null.
     */
    public static <T> Set<T> of(T value) {
        return Collections.singleton(value);
    }

    /**
     * Create new immutable Set. Values cannot be null.
     */
    @SafeVarargs
    public static <T> Set<T> of(T... values) {
        for (T value : values) {
            requireNonNull(value);
        }
        Set<T> set = new HashSet<>(Arrays.asList(values));
        return unmodifiableSet(set);
    }

    /**
     * Convert origin set to new immutable set.
     *
     * @return set contains the result.
     */
    public static <S, T> Set<T> convert(Set<S> set, Function<? super S, ? extends T> function) {
        requireNonNull(set);
        Set<T> newSet = new HashSet<>(calculateCapacity(set.size()));
        for (S e : set) {
            newSet.add(function.apply(e));
        }
        return unmodifiableSet(newSet);
    }

    /**
     * Convert origin set to new immutable set.
     *
     * @return set contains the result.
     * @deprecated use {@link #convert(Set, Function)}
     */
    @Deprecated
    public static <S, T> Set<T> convertTo(Set<S> set, Function<? super S, ? extends T> function) {
        return convert(set, function);
    }

    /**
     * Filter set, to new immutable set.
     *
     * @return set which contains the elements in origin set, and accepted by predicate
     */
    public static <T> Set<T> filter(Set<T> set, Predicate<? super T> predicate) {
        requireNonNull(set);
        Set<T> newSet = new HashSet<>(Math.min(calculateCapacity(set.size()), INIT_CAPACITY));
        for (T e : set) {
            if (predicate.test(e)) {
                newSet.add(e);
            }
        }
        return unmodifiableSet(newSet);
    }

    private static int calculateCapacity(int size) {
        return (int) (size / LOAD_FACTOR) + 1;
    }
}
