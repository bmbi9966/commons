package net.dongliu.commons.collection;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Immutable Pair/Tuple2. The key and value can not be null.
 *
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> implements Map.Entry<K, V>, Serializable {

    private static final long serialVersionUID = -6468644647295390377L;
    private final K key;
    private final V value;

    private Pair(K key, V value) {
        this.key = requireNonNull(key);
        this.value = requireNonNull(value);
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

    /**
     * The first value of this tuple
     */
    public K first() {
        return key;
    }

    /**
     * The second value of this tuple
     */
    public V second() {
        return value;
    }

    /**
     * Create one new pair, replace the first value with new value.
     */
    public Pair<K, V> withFirst(K first) {
        return new Pair<>(first, second());
    }

    /**
     * Create one new pair, replace the second value with new value.
     */
    public Pair<K, V> withSecond(V second) {
        return new Pair<>(first(), second);
    }

    /**
     * Append a new value.
     *
     * @param third the new value
     * @return Triple hold the Pair's value and new value
     */
    public <C> Triple<K, V, C> append(C third) {
        return Triple.of(first(), second(), third);
    }

    /**
     * Prepend a new value.
     *
     * @param value the new value
     * @return Triple hold new value and the Pair's value
     */
    public <A> Triple<A, K, V> prepend(A value) {
        return Triple.of(value, first(), second());
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
