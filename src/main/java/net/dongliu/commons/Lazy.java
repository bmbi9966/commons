package net.dongliu.commons;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Supplier that only compute value only once.
 * <p>
 * The passed in supplier should not return null value, or throw exception.
 * If error occurred when compute value, the exception would be thrown, and the next call will run the code again.
 * If computed value is null, a NPE would be thrown.
 * </p>
 * <p>
 * This class is thread safe.
 * </p>
 *
 * @param <T> the value type
 */
public class Lazy<T> implements Supplier<T> {

    private Supplier<T> supplier;
    private T value;

    private static final VarHandle valueHandle;

    static {
        try {
            valueHandle = MethodHandles.lookup()
                    .findVarHandle(Lazy.class, "value", Object.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // should not happen
            throw new RuntimeException(e);
        }
    }

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
        if (value == null) {
            synchronized (this) {
                if (value == null) {
                    T value = requireNonNull(supplier.get());
                    valueHandle.setVolatile(this, value);
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
