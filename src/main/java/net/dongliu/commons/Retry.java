package net.dongliu.commons;

import java.time.Duration;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * For retry execute code. This Class is immutable, and the instance should be reused.
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

    private Retry(Builder builder) {
        times = builder.times;
        intervalProvider = builder.intervalProvider;
        predicate = builder.predicate;
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
        requireNonNull(interval);
        return new Retry(times, i -> interval, e -> true);
    }

    /**
     * Create a new Builder with default initial settings.
     */
    public static Builder builder() {
        return new Builder();
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
     * 用于自定义Builder实例
     */
    public static final class Builder {
        private int times = 1;
        private IntFunction<Duration> intervalProvider = i -> Duration.ZERO;
        private Predicate<Throwable> predicate = e -> true;

        private Builder() {
        }

        /**
         * Set the total times may run the code. Default is 1.
         * If pass a value less than 1, will run the code one and only one time.
         */
        public Builder times(int times) {
            this.times = times;
            return this;
        }

        /**
         * Provide interval between retries
         */
        public Builder intervalProvider(IntFunction<Duration> intervalProvider) {
            this.intervalProvider = requireNonNull(intervalProvider);
            return this;
        }

        /**
         * Set interval between retries
         */
        public Builder interval(Duration interval) {
            requireNonNull(interval);
            return intervalProvider(i -> interval);
        }

        /**
         * Retry when exception thrown pass the test of predicate.
         */
        public Builder retryIf(Predicate<Throwable> predicate) {
            this.predicate = requireNonNull(predicate);
            return this;
        }

        /**
         * Retry when thrown exception is or sub of given exception type.
         */
        public Builder retryIfThrown(Class<? extends Throwable> cls) {
            return retryIf(cls::isInstance);
        }

        /**
         * Create a new Retry instance with settings.
         */
        public Retry build() {
            return new Retry(this);
        }
    }

}
