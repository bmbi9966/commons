package net.dongliu.commons.concurrent;

import java.time.Duration;

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
        sleepNanos(millis * 1000_000);
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
        var untilNanos = System.nanoTime() + nanos;
        long toSleepNanos = nanos;
        do {
            try {
                sleepNanos0(toSleepNanos);
            } catch (InterruptedException ignore) {
            }
            toSleepNanos = untilNanos - System.nanoTime();

        } while (toSleepNanos > 10);
    }

    /**
     * Sleep with nanos uniterruptablly
     *
     * @param nanos the time to sleep
     */
    public static void sleepNanos0(long nanos) throws InterruptedException {
        Thread.sleep(nanos / 1000_000, (int) (nanos % 1000_000));
    }

}
