package net.dongliu.commons.collection;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable Pair. The key and value can not be null.
 *
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> implements Map.Entry<K, V>, Serializable {

    private static final long serialVersionUID = -6468644647295390377L;
    private final K key;
    private final V value;

    private Pair(K key, V value) {
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Create a new pair
     */
    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    /**
     * Create one new pair, replace the key with newKey.
     */
    public Pair<K, V> withKey(K newKey) {
        return new Pair<>(newKey, value);
    }

    /**
     * Create one new pair, replace the value with newValue.
     */
    public Pair<K, V> withValue(V newValue) {
        return new Pair<>(key, newValue);
    }

    public K getFirst() {
        return key;
    }

    public V getSecond() {
        return value;
    }

    /**
     * Create one new pair, replace the key with newKey.
     */
    public Pair<K, V> withFirst(K first) {
        return new Pair<>(first, value);
    }

    /**
     * Create one new pair, replace the value with newValue.
     */
    public Pair<K, V> withSecond(V second) {
        return new Pair<>(key, second);
    }

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) &&
                Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
