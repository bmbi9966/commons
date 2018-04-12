package net.dongliu.commons;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Supplier that only compute value once.
 *
 * @param <T>
 */
public class Lazy<T> implements Supplier<T> {

    private volatile boolean init;
    private T value;
    private Throwable throwable;
    private final Supplier<T> supplier;

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Create one new Lazy instance.
     *
     * @param supplier provider the value
     * @param <T>      the value type
     * @return
     */
    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(Objects.requireNonNull(supplier));
    }

    @Override
    public T get() {
        if (!init) {
            synchronized (this) {
                if (!init) {
                    try {
                        this.value = supplier.get();
                    } catch (Throwable t) {
                        this.throwable = t;
                    } finally {
                        init = true;
                    }
                }
            }
        }
        if (value != null) {
            return value;
        }
        throw Throwables.sneakyThrow(this.throwable);
    }
}
