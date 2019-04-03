package net.dongliu.commons.concurrent;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Utils for thread
 */
public class Threads {

    /**
     * Start a new thread to run the runnable
     *
     * @param runnable the runnable to run
     * @return the new thread
     */
    public static Thread start(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    /**
     * Start a thread, run task async, and return a future. When task finished, the thread exits.
     */
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        var future = new CompletableFuture<Void>();
        Thread thread = new Thread(() -> {
            try {
                runnable.run();
                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        thread.start();
        return future;
    }

    /**
     * Start a thread, run task async, and return future contains the result. When task finished, the thread exits.
     */
    public static <T> CompletableFuture<T> callAsync(Callable<T> callable) {
        var future = new CompletableFuture<T>();
        Thread thread = new Thread(() -> {
            try {
                T result = callable.call();
                future.complete(result);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        thread.start();
        return future;
    }

    /**
     * Sleep with nanos uniterruptablly
     *
     * @param duration the time to sleep
     */
    public static void sleep(Duration duration) {
        if (duration.compareTo(Duration.ZERO) < 0) {
            throw new IllegalArgumentException("timeout value is negative: " + duration);
        }
        sleepNanos(duration.toNanos());
    }

    /**
     * Sleep with seconds uniterruptablly
     *
     * @param seconds the time to sleep
     */
    public static void sleepSeconds(double seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("timeout value is negative: " + seconds);
        }
        sleepNanos((long) (seconds * 1000_000_000L));
    }

    /**
     * Sleep with millis uniterruptablly
     *
     * @param millis the time to sleep
     */
    public static void sleepMills(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative: " + millis);
        }
        sleepNanos(TimeUnit.MILLISECONDS.toNanos(millis));
    }

    /**
     * Sleep with nanos uniterruptablly
     *
     * @param nanos the time to sleep
     */
    public static void sleepNanos(long nanos) {
        if (nanos < 0) {
            throw new IllegalArgumentException("timeout value is negative: " + nanos);
        }
        if (nanos == 0) {
            return;
        }
        var untilNanos = System.nanoTime() + nanos;
        long toSleepNanos = nanos;
        boolean interrupted = false;
        do {
            try {
                TimeUnit.NANOSECONDS.sleep(toSleepNanos);
            } catch (InterruptedException e) {
                interrupted = true;
            }
            toSleepNanos = untilNanos - System.nanoTime();

        } while (toSleepNanos > 0);
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
    }

}
