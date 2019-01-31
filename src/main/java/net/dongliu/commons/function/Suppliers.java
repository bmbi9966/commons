package net.dongliu.commons.function;

import net.dongliu.commons.Lazy;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Utils for Supplier
 */
public class Suppliers {

    /**
     * Return a thread-safe Supplier which only calculate once.
     * <p>
     * The passed in supplier should not return null value, or throw exception.
     * If error occurred when compute value, the exception would be thrown, and the next call will run the code again.
     * If computed value is null, a NPE would be thrown.
     * </p>
     */
    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        requireNonNull(supplier);
        if (supplier instanceof Lazy) {
            return supplier;
        }
        return Lazy.of(supplier);
    }
}
