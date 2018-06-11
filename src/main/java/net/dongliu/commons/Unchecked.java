package net.dongliu.commons;

import java.util.function.*;

/**
 * Wrap checked interface to unchecked interface.
 * If exception is caught, both checked exceptions and unchecked exceptions will be thrown silently.
 */
public class Unchecked {
    private Unchecked() {
    }

    /**
     * Run a block of code
     *
     * @param runnable the code to run
     */
    public static void run(ERunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw Throwables.sneakyThrow(e);
        }
    }

    /**
     * Call a block of code, return result
     *
     * @param supplier the code to run
     */
    public static <T> T call(ESupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw Throwables.sneakyThrow(e);
        }
    }

    @FunctionalInterface
    public interface ERunnable {
        void run() throws Exception;
    }

    /**
     * Wrap to Runnable interface
     *
     * @return wrapped runnable
     */
    public static Runnable runnable(ERunnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw Throwables.sneakyThrow(e);
            }
        };
    }

    public interface ESupplier<T> {
        T get() throws Exception;
    }

    /**
     * Wrap to Supplier interface
     *
     * @return
     */
    public static <T> Supplier<T> supplier(ESupplier<T> supplier) {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw Throwables.sneakyThrow(e);
            }
        };
    }

    @FunctionalInterface
    public interface EConsumer<T> {
        void accept(T value) throws Exception;
    }

    /**
     * Wrap to Consumer interface
     *
     * @return
     */
    public static <T> Consumer<T> consumer(EConsumer<T> consumer) {
        return v -> {
            try {
                consumer.accept(v);
            } catch (Exception e) {
                throw Throwables.sneakyThrow(e);
            }
        };
    }

    @FunctionalInterface
    public interface EPredicate<T> {
        boolean test(T value) throws Exception;
    }

    /**
     * Wrap to Predicate interface
     *
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> predicate(EPredicate<T> predicate) {
        return v -> {
            try {
                return predicate.test(v);
            } catch (Exception e) {
                throw Throwables.sneakyThrow(e);
            }
        };
    }

    @FunctionalInterface
    public interface EFunction<T, R> {
        R apply(T value) throws Exception;
    }

    /**
     * Wrap to Function interface
     *
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Function<T, R> function(EFunction<T, R> function) {
        return v -> {
            try {
                return function.apply(v);
            } catch (Exception e) {
                throw Throwables.sneakyThrow(e);
            }
        };
    }

    @FunctionalInterface
    public interface EBiConsumer<T, U> {
        void accept(T v1, U v2) throws Exception;
    }

    /**
     * Wrap to BiConsumer interface
     *
     * @return
     */
    public static <T, U> BiConsumer<T, U> biConsumer(EBiConsumer<T, U> consumer) {
        return (v1, v2) -> {
            try {
                consumer.accept(v1, v2);
            } catch (Exception e) {
                throw Throwables.sneakyThrow(e);
            }
        };
    }

    @FunctionalInterface
    public interface EBiFunction<T, U, R> {
        R apply(T v1, U v2) throws Exception;
    }

    /**
     * Wrap to BiFunction interface
     *
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, U, R> BiFunction<T, U, R> biFunction(EBiFunction<T, U, R> function) {
        return (v1, v2) -> {
            try {
                return function.apply(v1, v2);
            } catch (Exception e) {
                throw Throwables.sneakyThrow(e);
            }
        };
    }

}
