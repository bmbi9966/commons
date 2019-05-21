package net.dongliu.commons.collection;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
            return Set.of();
        }
        return set;
    }

    /**
     * Create new empty HashSet
     */
    public static <T> HashSet<T> newHashSet() {
        return new HashSet<>();
    }

    /**
     * Create new HashSet.
     */
    public static <T> HashSet<T> newHashSet(T v) {
        HashSet<T> set = new HashSet<>();
        set.add(v);
        return set;
    }

    /**
     * Create new HashSet.
     */
    public static <T> HashSet<T> newHashSet(T v1, T v2) {
        HashSet<T> set = new HashSet<>();
        set.add(v1);
        set.add(v2);
        return set;
    }

    /**
     * Create new HashSet.
     */
    public static <T> HashSet<T> newHashSet(T v1, T v2, T v3) {
        HashSet<T> set = new HashSet<>();
        set.add(v1);
        set.add(v2);
        set.add(v3);
        return set;
    }

    /**
     * Create new HashSet.
     */
    public static <T> HashSet<T> newHashSet(T v1, T v2, T v3, T v4) {
        HashSet<T> set = new HashSet<>();
        set.add(v1);
        set.add(v2);
        set.add(v3);
        set.add(v4);
        return set;
    }

    /**
     * Create new HashSet.
     */
    public static <T> HashSet<T> newHashSet(T v1, T v2, T v3, T v4, T v5) {
        HashSet<T> set = new HashSet<>();
        set.add(v1);
        set.add(v2);
        set.add(v3);
        set.add(v4);
        set.add(v5);
        return set;
    }

    /**
     * Create new immutable List. Values cannot be null.
     * This method will do defensive copy for the param values.
     */
    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... values) {
        HashSet<T> set = new HashSet<>(Math.max(INIT_CAPACITY, (int) (values.length / LOAD_FACTOR)));
        Collections.addAll(set, values);
        return set;
    }

    /**
     * For easy set creation with initial values.
     *
     * @param supplier the sest supplier
     * @param values   the elements to add into set
     * @param <T>      element type
     * @return the set
     */
    @SafeVarargs
    public static <T, R extends Set<T>> R newSet(Supplier<R> supplier, T... values) {
        var set = supplier.get();
        Collections.addAll(set, values);
        return set;
    }

    /**
     * Create new immutable empty Set
     *
     * @deprecated using {@link Set#of()}
     */
    @Deprecated
    public static <T> Set<T> of() {
        return Set.of();
    }

    /**
     * Create new immutable empty Set. Values cannot be null.
     *
     * @deprecated using {@link Set#of(Object)}
     */
    @Deprecated
    public static <T> Set<T> of(T value) {
        return Set.of(value);
    }

    /**
     * Create new immutable Set. Values cannot be null.
     *
     * @deprecated using {@link Set#of(Object[])}
     */
    @Deprecated
    @SafeVarargs
    public static <T> Set<T> of(T... values) {
        return Set.of(values);
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
