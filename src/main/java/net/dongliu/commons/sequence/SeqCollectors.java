package net.dongliu.commons.sequence;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Common collectors for Sequence.
 */
public class SeqCollectors {
    private SeqCollectors() {
    }

    /**
     * Return a collector that return the max value in sequence as Result.
     * If sequence is empty, will return empty Optional
     *
     * @param <T> the element type
     */
    public static <T extends Comparable<T>> CollectConsumer<T, Optional<T>> max() {
        return new CollectConsumer<>() {
            private T value;

            @Override
            public void accept(T value) {
                requireNonNull(value);
                if (this.value == null || this.value.compareTo(value) < 0) {
                    this.value = value;
                }
            }

            @Override
            public Optional<T> finish() {
                return Optional.ofNullable(value);
            }
        };
    }

    /**
     * Return a collector that return the min value in sequence as Result.
     * If sequence is empty, will return empty Optional
     *
     * @param <T> the element type
     */
    public static <T extends Comparable<T>> CollectConsumer<T, Optional<T>> min() {
        return new CollectConsumer<>() {
            private T value;

            @Override
            public void accept(T value) {
                requireNonNull(value);
                if (this.value == null || this.value.compareTo(value) > 0) {
                    this.value = value;
                }
            }

            @Override
            public Optional<T> finish() {
                return Optional.ofNullable(value);
            }
        };
    }

    /**
     * Collect entry Sequence to  {@link Map}.
     *
     * @param mapSupplier to provider the map
     */
    public static <K, V> CollectConsumer<Map.Entry<K, V>, Map<K, V>> toMap(Supplier<Map<K, V>> mapSupplier) {
        requireNonNull(mapSupplier);
        return new CollectConsumer<>() {
            private Map<K, V> map = mapSupplier.get();

            @Override
            public void accept(Entry<K, V> entry) {
                map.put(entry.getKey(), entry.getValue());
            }

            @Override
            public Map<K, V> finish() {
                return map;
            }
        };
    }

    /**
     * Collect entry Sequence to {@link HashMap}.
     */
    public static <K, V> CollectConsumer<Map.Entry<K, V>, Map<K, V>> toHashMap() {
        return toMap(HashMap::new);
    }

    /**
     * Collect entry Sequence to immutable map.
     */
    public static <K, V> CollectConsumer<Map.Entry<K, V>, Map<K, V>> toImmutableMap() {
        return new CollectConsumer<>() {
            private Map<K, V> map = new HashMap<>();

            @Override
            public void accept(Entry<K, V> entry) {
                map.put(entry.getKey(), entry.getValue());
            }

            @Override
            public Map<K, V> finish() {
                return Map.copyOf(map);
            }
        };
    }
}
