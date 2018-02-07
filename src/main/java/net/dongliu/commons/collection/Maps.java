package net.dongliu.commons.collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utils method for Map
 */
public class Maps {

    /**
     * Create new immutable empty Map
     */
    public static <K, V> Map<K, V> of() {
        return Collections.emptyMap();
    }

    /**
     * Create new immutable Map
     */
    public static <K, V> Map<K, V> of(K key, V value) {
        return Collections.unmodifiableMap(newHashMap(key, value));
    }

    /**
     * Create new immutable Map
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2) {
        return Collections.unmodifiableMap(newHashMap(
                key1, value1,
                key2, value2
        ));
    }

    /**
     * Create new immutable Map
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3) {
        return Collections.unmodifiableMap(newHashMap(
                key1, value1,
                key2, value2,
                key3, value3
        ));
    }

    /**
     * Create new immutable Map
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3,
                                      K key4, V value4) {

        return Collections.unmodifiableMap(newHashMap(
                key1, value1,
                key2, value2,
                key3, value3,
                key4, value4
        ));
    }

    /**
     * Create new immutable Map
     */
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3,
                                      K key4, V value4,
                                      K key5, V value5) {

        return Collections.unmodifiableMap(newHashMap(
                key1, value1,
                key2, value2,
                key3, value3,
                key4, value4,
                key5, value5
        ));
    }

    /**
     * Create new immutable Map
     */
    @SafeVarargs
    public static <K, V> Map<K, V> ofEntries(Map.Entry<K, V>... entries) {

        return Collections.unmodifiableMap(createFromEntries(entries));
    }

    /**
     * Create new HashMap
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * Create new HashMap
     */
    public static <K, V> HashMap<K, V> newHashMap(K key, V value) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    /**
     * Create new HashMap
     */
    public static <K, V> HashMap<K, V> newHashMap(K key1, V value1,
                                                  K key2, V value2) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

    /**
     * Create new HashMap
     */
    public static <K, V> HashMap<K, V> newHashMap(K key1, V value1,
                                                  K key2, V value2,
                                                  K key3, V value3) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        return map;
    }

    /**
     * Create new HashMap
     */
    public static <K, V> HashMap<K, V> newHashMap(K key1, V value1,
                                                  K key2, V value2,
                                                  K key3, V value3,
                                                  K key4, V value4) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        map.put(key4, value4);
        return map;
    }

    /**
     * Create new HashMap
     */
    public static <K, V> HashMap<K, V> newHashMap(K key1, V value1,
                                                  K key2, V value2,
                                                  K key3, V value3,
                                                  K key4, V value4,
                                                  K key5, V value5) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        map.put(key4, value4);
        map.put(key5, value5);
        return map;
    }

    /**
     * Create new HashMap
     */
    @SafeVarargs
    public static <K, V> HashMap<K, V> createFromEntries(Map.Entry<K, V>... entries) {
        HashMap<K, V> map = new HashMap<>();
        for (Map.Entry<K, V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
