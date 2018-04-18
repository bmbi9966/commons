package net.dongliu.commons.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Utils method for Map
 */
public class Maps {

    /**
     * If map is null, return immutable empty map; else return map self.
     *
     * @param map the map
     * @return non-null map
     */
    @NotNull
    public static <K, V> Map<K, V> nullToEmpty(@Nullable Map<K, V> map) {
        if (map == null) {
            return of();
        }
        return map;
    }

    /**
     * Create new immutable empty Map
     */
    public static <K, V> Map<K, V> of() {
        return Collections.emptyMap();
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    public static <K, V> Map<K, V> of(K key, V value) {
        return Collections.singletonMap(requireNonNull(key), requireNonNull(value));
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2) {
        HashMap<K, V> map = new HashMap<>();
        map.put(requireNonNull(key1), requireNonNull(value1));
        map.put(requireNonNull(key2), requireNonNull(value2));
        return Collections.unmodifiableMap(map);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3) {
        HashMap<K, V> map = new HashMap<>();
        map.put(requireNonNull(key1), requireNonNull(value1));
        map.put(requireNonNull(key2), requireNonNull(value2));
        map.put(requireNonNull(key3), requireNonNull(value3));
        return Collections.unmodifiableMap(map);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3,
                                      K key4, V value4) {

        HashMap<K, V> map = new HashMap<>();
        map.put(requireNonNull(key1), requireNonNull(value1));
        map.put(requireNonNull(key2), requireNonNull(value2));
        map.put(requireNonNull(key3), requireNonNull(value3));
        map.put(requireNonNull(key4), requireNonNull(value4));
        return Collections.unmodifiableMap(map);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3,
                                      K key4, V value4,
                                      K key5, V value5) {
        HashMap<K, V> map = new HashMap<>();
        map.put(requireNonNull(key1), requireNonNull(value1));
        map.put(requireNonNull(key2), requireNonNull(value2));
        map.put(requireNonNull(key3), requireNonNull(value3));
        map.put(requireNonNull(key4), requireNonNull(value4));
        map.put(requireNonNull(key5), requireNonNull(value5));
        return Collections.unmodifiableMap(map);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     */
    @SafeVarargs
    public static <K, V> Map<K, V> ofEntries(Map.Entry<K, V>... entries) {
        HashMap<K, V> map = new HashMap<>();
        for (Map.Entry<K, V> entry : entries) {
            map.put(requireNonNull(entry.getKey()), requireNonNull(entry.getValue()));
        }
        return Collections.unmodifiableMap(map);
    }

}
