package net.dongliu.commons.collection;

import java.util.Collections;
import java.util.HashSet;

/**
 * Factory utils for HashSet.
 */
public class HashSets {

    private static final int DEFAULT_INIT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Create new empty HashSet
     */
    public static <T> HashSet<T> create() {
        return new HashSet<>();
    }

    /**
     * Create new HashSet.
     */
    public static <T> HashSet<T> create(T v) {
        HashSet<T> set = new HashSet<>();
        set.add(v);
        return set;
    }

    /**
     * Create new HashSet.
     */
    public static <T> HashSet<T> create(T v1, T v2) {
        HashSet<T> set = new HashSet<>();
        set.add(v1);
        set.add(v2);
        return set;
    }

    /**
     * Create new HashSet.
     */
    public static <T> HashSet<T> create(T v1, T v2, T v3) {
        HashSet<T> set = new HashSet<>();
        set.add(v1);
        set.add(v2);
        set.add(v3);
        return set;
    }

    /**
     * Create new HashSet.
     */
    public static <T> HashSet<T> create(T v1, T v2, T v3, T v4) {
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
    public static <T> HashSet<T> create(T v1, T v2, T v3, T v4, T v5) {
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
    public static <T> HashSet<T> create(T... values) {
        HashSet<T> set = new HashSet<>(Math.min(DEFAULT_INIT_CAPACITY, (int) (values.length / DEFAULT_LOAD_FACTOR)));
        Collections.addAll(set, values);
        return set;
    }
}
