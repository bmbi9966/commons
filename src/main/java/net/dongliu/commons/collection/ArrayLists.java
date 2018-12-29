package net.dongliu.commons.collection;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Factory utils for ArrayList.
 */
public class ArrayLists {

    /**
     * Create new empty ArrayList
     */
    public static <T> ArrayList<T> create() {
        return new ArrayList<>();
    }

    /**
     * Create new ArrayList.
     */
    public static <T> ArrayList<T> create(T v) {
        ArrayList<T> list = new ArrayList<>();
        list.add(v);
        return list;
    }

    /**
     * Create new ArrayList.
     */
    public static <T> ArrayList<T> create(T v1, T v2) {
        ArrayList<T> list = new ArrayList<>();
        list.add(v1);
        list.add(v2);
        return list;
    }

    /**
     * Create new ArrayList.
     */
    public static <T> ArrayList<T> create(T v1, T v2, T v3) {
        ArrayList<T> list = new ArrayList<>();
        list.add(v1);
        list.add(v2);
        list.add(v3);
        return list;
    }

    /**
     * Create new ArrayList.
     */
    public static <T> ArrayList<T> create(T v1, T v2, T v3, T v4) {
        ArrayList<T> list = new ArrayList<>();
        list.add(v1);
        list.add(v2);
        list.add(v3);
        list.add(v4);
        return list;
    }

    /**
     * Create new ArrayList.
     */
    public static <T> ArrayList<T> create(T v1, T v2, T v3, T v4, T v5) {
        ArrayList<T> list = new ArrayList<>();
        list.add(v1);
        list.add(v2);
        list.add(v3);
        list.add(v4);
        list.add(v5);
        return list;
    }

    /**
     * Create new immutable List. Values cannot be null.
     * This method will do defensive copy for the param values.
     */
    @SafeVarargs
    public static <T> ArrayList<T> create(T... values) {
        ArrayList<T> list = new ArrayList<>(Math.max(16, values.length));
        Collections.addAll(list, values);
        return list;
    }
}
