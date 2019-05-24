package net.dongliu.commons;

import net.dongliu.commons.function.Predicates;

import java.time.Duration;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * This is a immutable value class for retrying execute code.
 * The Retry instances should be reused if possible.
 */
public class Retry {
    private final int times;
    private final IntFunction<Duration> intervalProvider;
    // decode if retry by the exception thrown
    private final Predicate<Throwable> predicate;

    private Retry(int times, IntFunction<Duration> intervalProvider, Predicate<Throwable> predicate) {
        this.times = times;
        this.intervalProvider = intervalProvider;
        this.predicate = predicate;
    }

    /**
     * Create a retry instance with times, and no interval between retries.
     *
     * @param times the total times may run the code. If pass a value less than 1, will run the code one and only one time.
     * @return new Retry instance
     */
    public static Retry of(int times) {
        return of(times, Duration.ZERO);
    }

    /**
     * Create a retry instance with times, and interval provided by intervalProvider.
     *
     * @param times    the total times may run the code. If pass a value less than 1, will run the code one and only one time.
     * @param interval interval between retries
     * @return new Retry instance
     */
    public static Retry of(int times, Duration interval) {
        return new Retry(times, t -> interval, Predicates.alwaysTrue());
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
                if (predicate.test(e)) {
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


    /**
     * Return a new Retry with intervalProvider.
     *
     * @param intervalProvider Provide interval between retries
     */
    public Retry withIntervalProvider(IntFunction<Duration> intervalProvider) {
        requireNonNull(intervalProvider);
        return new Retry(times, intervalProvider, predicate);
    }

    /**
     * Return a new Retry with new interval.
     */
    public Retry withInterval(Duration interval) {
        requireNonNull(interval);
        return withIntervalProvider(t -> interval);
    }

    /**
     * Return a new Retry with new retry predicate.
     * When exception thrown pass the test of predicate(and not exceed the times limit) retry would be done
     *
     * @param predicate thr predicate
     */
    public Retry retryIf(Predicate<Throwable> predicate) {
        requireNonNull(intervalProvider);
        return new Retry(times, intervalProvider, predicate);
    }

    /**
     * Return a new Retry which would retry when thrown exception is or sub of given exception type.
     */
    public Retry retryIfThrown(Class<? extends Throwable> cls) {
        return retryIf(cls::isInstance);
    }

}
