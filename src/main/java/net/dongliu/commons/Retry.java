package net.dongliu.commons;

import java.time.Duration;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * For retry execute code. This Class is immutable, and the instance should be reused.
 */
public class Retry {
    private final int times;
    private final IntFunction<Duration> intervalProvider;
    private final Class<? extends Throwable> exceptionType;

    private Retry(int times, IntFunction<Duration> intervalProvider, Class<? extends Throwable> exceptionType) {
        this.times = times;
        this.intervalProvider = intervalProvider;
        this.exceptionType = exceptionType;
    }

    /**
     * Create a retrier with times, and no interval between retries.
     *
     * @param times retry times
     * @return new Retry instance
     */
    public static Retry of(int times) {
        return of(times, i -> Duration.ZERO);
    }

    /**
     * Create a retrier with times, and interval provided by intervalProvider.
     *
     * @param times            retry times
     * @param intervalProvider provide interval between retries
     * @return new Retry instance
     */
    public static Retry of(int times, IntFunction<Duration> intervalProvider) {
        requireNonNull(intervalProvider);
        if (times <= 0) {
            throw new IllegalArgumentException("illegal times: " + times);
        }
        return new Retry(times, intervalProvider, Exception.class);
    }

    /**
     * Retry only when thrown exception is specific type.
     * Default is {@link Exception}, which means will not retry when {@link Error} is thrown.
     */
    public Retry onException(Class<? extends Throwable> exceptionType) {
        requireNonNull(exceptionType);
        return new Retry(times, intervalProvider, exceptionType);
    }

    /**
     * Run a runnable, until succeed, or exceed retry times.
     *
     * @param runnable the code to be run
     */
    public void run(Runnable runnable) {
        for (int i = 0; i < times - 1; i++) {
            try {
                runnable.run();
                return;
            } catch (Throwable e) {
                if (exceptionType.isInstance(e)) {
                    try {
                        Thread.sleep(intervalProvider.apply(i).toMillis());
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw e;
                    }
                    continue;
                }
                throw e;
            }
        }
        // the last time run
        runnable.run();
    }


    /**
     * Run a runnable, until succeed, or exceed retry times.
     *
     * @param <T>      the return value type
     * @param supplier the code to be run
     * @return value returned by supplier
     */
    public <T> T call(Supplier<T> supplier) {
        for (int i = 0; i < times - 1; i++) {
            try {
                return supplier.get();
            } catch (Exception e) {
                try {
                    Thread.sleep(intervalProvider.apply(i).toMillis());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }
        }
        // the last time run
        return supplier.get();
    }
}
