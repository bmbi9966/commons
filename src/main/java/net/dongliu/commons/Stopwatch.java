package net.dongliu.commons;

import java.time.Duration;

/**
 * A tool for measuring elapsed time. This class is not thread-safe.
 */
public class Stopwatch {

    // nanos when start the stopwatch
    private long startNanos;
    // 0 init; 1 started; 2 stopped
    private int status;
    // elapsed nanos when stop method is called
    private long stopElapesdNanos;

    private Stopwatch() {
    }

    /**
     * Create a stopwatch, not started.
     * You may want to call start() method to start this stopwatch.
     */
    public static Stopwatch create() {
        return new Stopwatch();
    }

    /**
     * Start the watch
     */
    public Stopwatch start() {
        if (this.status == 1) {
            throw new IllegalStateException("Stopwatch already started.");
        }
        this.startNanos = System.nanoTime();
        this.status = 1;
        return this;
    }

    /**
     * Stop the stopwatch, the following calls to elapsed will return elapsed time as this time.
     */
    public Stopwatch stop() {
        if (this.status != 1) {
            throw new IllegalStateException("Stop watch not started");
        }
        this.stopElapesdNanos = System.nanoTime() - startNanos;
        this.status = 2;
        return this;
    }

    /**
     * Return clasped duration.
     */
    public Duration elasped() {
        return Duration.ofNanos(elapsedNanos());
    }

    /**
     * Return clasped duration as nano seconds.
     */
    public long elapsedNanos() {
        if (status == 0) {
            throw new IllegalStateException("Stopwatch not started");
        }
        if (status == 1) {
            return System.nanoTime() - startNanos;
        } else if (status == 2) {
            return stopElapesdNanos;
        }

        throw new RuntimeException("should not happen, status: " + status);
    }

    /**
     * Return clasped duration as milli seconds.
     */
    public long elaspedMillis() {
        return elapsedNanos() / 1000_000;
    }
}
