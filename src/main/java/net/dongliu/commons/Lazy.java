package net.dongliu.commons;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Supplier that only compute value once. The computed value cannot be null.
 *
 * @param <T>
 */
public class Lazy<T> implements Supplier<T> {


    private volatile T value;
    private final Supplier<T> supplier;

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(Objects.requireNonNull(supplier));
    }

    @Override
    public T get() {
        if (value == null) {
            synchronized (this) {
                if (value == null) {
                    T value = supplier.get();
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.value = value;
                }
            }

        }
        return value;
    }
}
