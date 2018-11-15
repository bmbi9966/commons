package net.dongliu.commons.collection;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Factory utils for HashMap
 */
public class HashMaps {

    private static final int DEFAULT_INIT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Create new HashMap.
     */
    public static <K, V> HashMap<K, V> create() {
        return new HashMap<>();
    }

    /**
     * Create new HashMap.
     */
    @SafeVarargs
    public static <K, V> HashMap<K, V> create(Map.Entry<K, V>... entries) {
        int initSize = Math.max(DEFAULT_INIT_CAPACITY, (int) (entries.length / DEFAULT_LOAD_FACTOR));
        HashMap<K, V> map = new HashMap<>(initSize, DEFAULT_LOAD_FACTOR);
        for (Map.Entry<K, V> entry : entries) {
            map.put(requireNonNull(entry.getKey()), requireNonNull(entry.getValue()));
        }
        return map;
    }

}
