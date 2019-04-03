package net.dongliu.commons;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * A tool for measuring elapsed time. This class is not thread-safe.
 */
public class Stopwatch {

    // nanos when start the stopwatch
    private long startNanos;
    // 0 init; 1 started; 2 stopped
    private int status;
    // elapsed nanos when stop method is called
    private long stopElapsedNanos;

    private static final int STATUS_UNSTARTED = 0;
    private static final int STATUS_STARTED = 1;
    private static final int STATUS_STOPPED = 2;

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
        if (this.status == STATUS_STARTED) {
            throw new IllegalStateException("Stopwatch already started.");
        }
        this.startNanos = System.nanoTime();
        this.status = STATUS_STARTED;
        return this;
    }

    /**
     * Stop the stopwatch, the following calls to elapsed will return elapsed time as this time.
     */
    public Stopwatch stop() {
        if (this.status != STATUS_STARTED) {
            throw new IllegalStateException("Stop watch not started, status: " + status);
        }
        this.stopElapsedNanos = System.nanoTime() - startNanos;
        this.status = STATUS_STOPPED;
        return this;
    }

    /**
     * Return elapsed duration.
     */
    public Duration elapsed() {
        return Duration.ofNanos(elapsedNanos());
    }

    /**
     * Return elapsed duration as specific time unit value.
     */
    public long elapsed(TimeUnit timeUnit) {
        return timeUnit.convert(elapsedNanos(), TimeUnit.NANOSECONDS);
    }

    /**
     * Return clasped duration as nano seconds.
     */
    public long elapsedNanos() {
        switch (status) {
            case STATUS_UNSTARTED:
                throw new IllegalStateException("Stopwatch not started");
            case STATUS_STARTED:
                return System.nanoTime() - startNanos;
            case STATUS_STOPPED:
                return stopElapsedNanos;
            default:
                throw new IllegalStateException("should not happen, status: " + status);
        }

    }

    /**
     * Return clasped duration as milli seconds.
     */
    public long elapsedMillis() {
        return elapsed(TimeUnit.MILLISECONDS);
    }
}
