package net.dongliu.commons.collection;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Collections.singletonMap;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

/**
 * Utils method for Map
 */
public class Maps {

    private static final int DEFAULT_INIT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * If map is null, return immutable empty map; else return map self.
     *
     * @param map the map
     * @return non-null map
     */
    public static <K, V> Map<K, V> nullToEmpty(@Nullable Map<K, V> map) {
        if (map == null) {
            return Map.of();
        }
        return map;
    }

    /**
     * Create new HashMap.
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * Create new HashMap.
     */
    @SafeVarargs
    public static <K, V> HashMap<K, V> newHashMap(Map.Entry<? extends K, ? extends V>... entries) {
        return newMap(HashMap::new, entries);
    }

    /**
     * Create a mutable, case insensitive map.
     */
    public static <V> Map<String, V> newCaseInsensitiveMap() {
        return new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Create a mutable, case insensitive map.
     */
    @SafeVarargs
    public static <V> Map<String, V> newCaseInsensitiveMap(Map.Entry<String, ? extends V>... entries) {
        return newMap(Maps::newCaseInsensitiveMap, entries);
    }

    /**
     * For easy map creation with initial values.
     *
     * @param supplier the map supplier
     * @param entries  the entries to put into map
     * @param <K>      key type
     * @param <V>      value type
     * @return the map
     */
    @SafeVarargs
    public static <K, V, R extends Map<K, V>> R newMap(Supplier<R> supplier,
                                                       Map.Entry<? extends K, ? extends V>... entries) {
        var map = supplier.get();
        for (var entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * Create new immutable empty Map
     *
     * @deprecated using {@link Map#of()}
     */
    @Deprecated
    public static <K, V> Map<K, V> of() {
        return Map.of();
    }

    /**
     * Create new immutable Map. Values cannot be null.
     *
     * @deprecated using {@link Map#of(Object, Object)}
     */
    @Deprecated
    public static <K, V> Map<K, V> of(K key, V value) {
        return singletonMap(requireNonNull(key), requireNonNull(value));
    }

    /**
     * Create new immutable Map. Values cannot be null.
     *
     * @deprecated using {@link Map#of(Object, Object, Object, Object)}
     */
    @Deprecated
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2) {
        return Map.of(key1, value1, key2, value2);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     *
     * @deprecated using {@link Map#of(Object, Object, Object, Object, Object, Object)}
     */
    @Deprecated
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3) {
        return Map.of(key1, value1, key2, value2, key3, value3);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     *
     * @deprecated using {@link Map#of(Object, Object, Object, Object, Object, Object, Object, Object)}
     */
    @Deprecated
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3,
                                      K key4, V value4) {

        return Map.of(key1, value1, key2, value2, key3, value3, key4, value4);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     *
     * @deprecated using {@link Map#of(Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object)}
     */
    @Deprecated
    public static <K, V> Map<K, V> of(K key1, V value1,
                                      K key2, V value2,
                                      K key3, V value3,
                                      K key4, V value4,
                                      K key5, V value5) {
        return Map.of(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5);
    }

    /**
     * Create new immutable Map. Values cannot be null.
     *
     * @deprecated using {@link Map#ofEntries(Entry[])}
     */
    @Deprecated
    @SafeVarargs
    public static <K, V> Map<K, V> ofEntries(Map.Entry<? extends K, ? extends V>... entries) {
        return Map.ofEntries(entries);
    }

    /**
     * Convert map to new immutable map, with key converted by keyMapper, and value converted by valueMapper.
     *
     * @param map         the original map
     * @param valueMapper the function to convert map value
     * @param <K>         the key type
     * @param <R>         the target key type
     * @param <V>         the original value type
     * @param <U>         the target value type
     * @return new map
     */
    public static <K, R, V, U> Map<R, U> convert(Map<? extends K, ? extends V> map,
                                                 Function<? super K, ? extends R> keyMapper,
                                                 Function<? super V, ? extends U> valueMapper) {
        requireNonNull(map);
        requireNonNull(keyMapper);
        requireNonNull(valueMapper);
        if (map.isEmpty()) {
            return Map.of();
        }
        Map<R, U> result = new HashMap<>();
        map.forEach((k, v) -> result.put(keyMapper.apply(k), valueMapper.apply(v)));
        return unmodifiableMap(result);
    }

    /**
     * Convert map to new immutable map, with key not modified, and value converted by function.
     *
     * @param map         the original map
     * @param valueMapper the function to convert map value
     * @param <K>         the key type
     * @param <V>         the original value type
     * @param <U>         the target value type
     * @return new map
     */
    public static <K, V, U> Map<K, U> convert(Map<? extends K, ? extends V> map,
                                              Function<? super V, ? extends U> valueMapper) {
        requireNonNull(map);
        requireNonNull(valueMapper);
        return convert(map, k -> k, valueMapper);
    }

    /**
     * Filter map to new immutable map, contains the entries accepted by predicate.
     *
     * @param map       the origin map
     * @param predicate the predicate to filter map entries
     * @param <K>       the key type
     * @param <V>       the value type
     * @return the new map
     */
    public static <K, V> Map<K, V> filter(Map<? extends K, ? extends V> map,
                                          BiPredicate<? super K, ? super V> predicate) {
        requireNonNull(map);
        requireNonNull(predicate);
        if (map.isEmpty()) {
            return Map.of();
        }
        Map<K, V> result = new HashMap<>();
        for (var entry : map.entrySet()) {
            if (predicate.test(entry.getKey(), entry.getValue())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return unmodifiableMap(result);
    }

    /**
     * Merge tow maps, to a new immutable map, contains key-value in both map. If map1 and map2 contains the same key,
     * the value from map2 will be used.
     *
     * @param map1 map1
     * @param map2 map2
     * @param <K>  map key type
     * @param <V>  map value type
     * @return new map
     */
    public static <K, V> Map<K, V> merge(Map<? extends K, ? extends V> map1, Map<? extends K, ? extends V> map2) {
        requireNonNull(map1);
        requireNonNull(map2);
        Map<K, V> map = new HashMap<>();
        map.putAll(map1);
        map.putAll(map2);
        return unmodifiableMap(map);
    }

    /**
     * Create new immutable map from a collection of keys, the values are constructed by valueMaker.
     *
     * @param elements     the key collections
     * @param keyRetriever to get key from elements
     * @param valueMaker   to make value from elements
     * @param <K>          key type
     * @param <V>          value type
     * @return new map
     */
    public static <T, K, V> Map<K, V> from(Collection<? extends T> elements,
                                           Function<? super T, ? extends K> keyRetriever,
                                           Function<? super T, ? extends V> valueMaker) {
        requireNonNull(elements);
        requireNonNull(valueMaker);
        if (elements.isEmpty()) {
            return Map.of();
        }
        Map<K, V> map = new HashMap<>();
        for (T e : elements) {
            map.put(keyRetriever.apply(e), valueMaker.apply(e));
        }
        return unmodifiableMap(map);
    }

    /**
     * Create new immutable map from a collection of keys, the values are constructed by valueMaker.
     *
     * @param keys       the key collections
     * @param valueMaker to make value from key
     * @param <K>        key type
     * @param <V>        value type
     * @return new map
     */
    public static <K, V> Map<K, V> fromKeys(Collection<? extends K> keys, Function<? super K, ? extends V> valueMaker) {
        return from(keys, Function.identity(), valueMaker);
    }

    /**
     * Create new immutable map from a collection of values, the values are constructed by keyMaker.
     *
     * @param values   the value collections
     * @param keyRetriever to get key from value
     * @param <K>      key type
     * @param <V>      value type
     * @return new map
     */
    public static <K, V> Map<K, V> fromValues(Collection<? extends V> values,
                                              Function<? super V, ? extends K> keyRetriever) {
        return from(values, keyRetriever, Function.identity());
    }
}
