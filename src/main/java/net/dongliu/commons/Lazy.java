package net.dongliu.commons;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Supplier that only compute value once, despite succeed or fail(thrown Exceptions).
 * The Lazy supplier also cache the Exception thrown at the initial compute phase, and throw it when meet following calls.
 * This class is ThreadSafe.
 * This class is not serializable.
 *
 * @param <T> the value type
 */
public class Lazy<T> implements Supplier<T> {

    private volatile boolean init;
    private Supplier<T> supplier;
    private T value;

    private Lazy(Supplier<T> supplier) {
        this.supplier = requireNonNull(supplier);
    }

    /**
     * Create one new Lazy instance.
     *
     * @param supplier provider the value
     * @param <T>      the value type
     * @return the created lazy value
     */
    public static <T> Lazy<T> of(Supplier<T> supplier) {
        requireNonNull(supplier);
        if (supplier instanceof Lazy) {
            return (Lazy<T>) supplier;
        }
        return new Lazy<>(requireNonNull(supplier));
    }

    @Override
    public T get() {
        if (!init) {
            synchronized (this) {
                if (!init) {
                    this.value = supplier.get();
                    this.init = true;
                }
            }
        }
        return this.value;
    }

    /**
     * Create a new lazy value, with value is calculated using function
     *
     * @param function the function to calculate value
     * @param <R>      new value type
     * @return the new lazy value
     */
    public <R> Lazy<R> map(Function<? super T, ? extends R> function) {
        return Lazy.of(() -> function.apply(get()));
    }
}
