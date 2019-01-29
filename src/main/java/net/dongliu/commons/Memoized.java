package net.dongliu.commons;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Utils for functional style memoize operator.
 */
public class Memoized {
    /**
     * Return a thread-safe runnable which only run once.
     * <p>
     * The runnable is guaranteed to finish executing when call memoized runnable finished.
     * </p>
     * <p>
     * The passed in runnable should not  throw exception.
     * If error occurred when run the code, the exception would be thrown, and the next call will run the code again.
     * </p>
     */
    public static Runnable runnable(Runnable runnable) {
        requireNonNull(runnable);
        return new MemoizedRunnable(runnable);
    }

    private static class MemoizedRunnable implements Runnable {
        private final Runnable runnable;
        private volatile boolean run;

        private MemoizedRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            if (!run) {
                synchronized (this) {
                    if (!run) {
                        runnable.run();
                        run = true;
                    }
                }
            }
        }
    }

    /**
     * Return a thread-safe Supplier which only calculate once.
     * <p>
     * The passed in supplier should not return null value, or throw exception.
     * If error occurred when compute value, the exception would be thrown, and the next call will run the code again.
     * If computed value is null, a NPE would be thrown.
     * </p>
     */
    public static <T> Supplier<T> supplier(Supplier<T> supplier) {
        return Lazy.of(supplier);
    }


    /**
     * Return a thread-safe Function which only calculate once for each key.
     * <p>
     * The passed in function should not return null value, or throw exception.
     * If error occurred when compute value, the exception would be thrown, and the next call will run the code again.
     * If computed value is null, a NPE would be thrown.
     * </p>
     */
    public static <T, R> Function<T, R> function(Function<T, R> function) {
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
