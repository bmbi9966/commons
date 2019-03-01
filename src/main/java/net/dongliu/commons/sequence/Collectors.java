package net.dongliu.commons.sequence;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Common collectors.
 */
public class Collectors {
    private Collectors() {
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
}
