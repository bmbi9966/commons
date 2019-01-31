package net.dongliu.commons.function;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Utils for Function
 */
public class Functions {
    /**
     * Return a thread-safe Function which only calculate once for each key.
     * <p>
     * The passed in function should not return null value, or throw exception.
     * If error occurred when compute value, the exception would be thrown, and the next call will run the code again.
     * If computed value is null, a NPE would be thrown.
     * </p>
     */
    public static <T, R> Function<T, R> memoize(Function<T, R> function) {
        Objects.requireNonNull(function);
        if (function instanceof MemoizedFunction) {
            return function;
        }
        return new MemoizedFunction<>(function);
    }

    private static class MemoizedFunction<T, R> implements Function<T, R> {

        private final Function<T, R> function;
        private final ConcurrentMap<T, R> map = new ConcurrentHashMap<>();

        private MemoizedFunction(Function<T, R> function) {
            this.function = function;
        }

        @Override
        public R apply(T t) {
            requireNonNull(t);
            // this map would not remove elements, so we can eliminate lock cost when element exists by getting the element
            // before call computeIfAbsent
            R value = map.get(t);
            if (value != null) {
                return value;
            }
            value = map.computeIfAbsent(t, function);
            requireNonNull(value);
            return value;
        }
    }
}
