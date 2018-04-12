package net.dongliu.commons;

import net.dongliu.commons.annotation.NonNull;
import net.dongliu.commons.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * Jdk already has one Objects class, So named as Objects2
 */
public class Objects2 {

    /**
     * If value1 is not null, use return value1; else return values2
     *
     * @param <T> the value type
     * @return non-null value
     * @throws NullPointerException if value2 is null
     */
    @NonNull
    public static <T> T firstNonNull(@Nullable T value1, @NonNull T value2) {
        if (value1 != null) {
            return value1;
        }
        return requireNonNull(value2);
    }
}
