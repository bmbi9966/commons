package net.dongliu.commons;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static net.dongliu.commons.Throwables.sneakyThrow;

/**
 * Supplier that only compute value once, despite succeed or fail(thrown Exceptions).
 * The Lazy supplier also cache the Exception thrown at the initial compute phase, and throw it when meet following calls.
 *
 * @param <T> the value type
 */
public class Lazy<T> implements Supplier<T>, Serializable {

    private static final long serialVersionUID = -7365971074093939980L;
    private transient volatile boolean init;
    private transient T value;
    private transient Throwable throwable;
    private final Supplier<T> supplier;

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
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
        // always should be unchecked exception
        throw sneakyThrow(this.throwable);
    }

    /**
     * Create a new lazy value, with value is calculated using function
     *
     * @param function the function to calculate value
     * @param <R>      new value type
     * @return the new lazy value
     */
    public <R> Lazy<R> map(Function<T, R> function) {
        return Lazy.of(() -> function.apply(get()));
    }
}
