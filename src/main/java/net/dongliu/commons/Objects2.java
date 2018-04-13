package net.dongliu.commons;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Jdk already has one Objects class, So named as Objects2
 */
public class Objects2 {

    /**
     * Elvis operator.
     * If value1 is not null, use return value1; else return values2.
     *
     * @param <T> the value type
     * @return non-null value
     * @throws NullPointerException if value2 is null
     */
    @Nonnull
    public static <T> T elvis(@Nullable T value1, @Nonnull T value2) {
        if (value1 != null) {
            return value1;
        }
        return requireNonNull(value2);
    }

    /**
     * Elvis operator.
     * If value1 is not null, use return value1; else use value calculate by supplier.
     *
     * @param supplier to calculate non-null value
     * @param <T>      the value type
     * @return non-null value
     * @throws NullPointerException if supplier is null or produce null value
     */
    @Nonnull
    public static <T> T elvis(@Nullable T value1, Supplier<T> supplier) {
        if (value1 != null) {
            return value1;
        }
        return requireNonNull(supplier.get());
    }
}
