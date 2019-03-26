package net.dongliu.commons;

import net.dongliu.commons.concurrent.Threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * For limiting ops count in specific time span
 */
public abstract class RateLimiter {

    private final double permitsPerSecond;
    private final long maxReserved;
    private final double nanosPerPermits;

    private final Lock lock = new ReentrantLock();
    // following should be accessed with lock
    private long lastNanos;


    private RateLimiter(double permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
        this.nanosPerPermits = 1000_000_000 / permitsPerSecond;
        this.lastNanos = System.nanoTime();
        this.maxReserved = Math.max((long) (permitsPerSecond * 3), 1);
    }

    /**
     * Create a new RateLimiter
     *
     * @param permitsPerSecond how many permits should generated in one second, should be larger than(or eq) zero
     * @throws IllegalArgumentException if permits value is invalid
     */
    public static RateLimiter of(double permitsPerSecond) {
        return new RateLimiter(checkPermits(permitsPerSecond)) {};
    }

    private static double checkPermits(double permits) {
        if (permits < 0) {
            throw new IllegalArgumentException("illegal permits: " + permits);
        }
        return permits;
    }

    /**
     * Acquire one permit, block if needed
     */
    public void acquire() {
        acquire(1);
    }

    /**
     * Acquire n permits, block if needed
     */
    public void acquire(int n) {
        checkCount(n);
        long nanosToSleep = acquireInternal(n, false);
        if (nanosToSleep > 0) {
            Threads.sleepNanos(nanosToSleep);
        }
    }

    public long acquireInternal(int n, boolean isTry) {
        long nanos = (long) (n * nanosPerPermits);
        lock.lock();
        try {
            long current = System.nanoTime();
            var lastNanosMin = current - (long) (maxReserved * nanosPerPermits);
            if (lastNanosMin > this.lastNanos) {
                this.lastNanos = lastNanosMin;
            }
            long newNanos = this.lastNanos + nanos;
            long nanosToSleep = newNanos - current;
            if (nanosToSleep < 0 || !isTry) {
                this.lastNanos = newNanos;
            }
            return nanosToSleep;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Try to acquire one permit, not block.
     *
     * @return true if acquire permits succeed, false if do not have enough available permits.
     */
    public boolean tryAcquire() {
        return tryAcquire(1);
    }

    /**
     * Try to acquire n permits, not block.
     *
     * @return true if acquire permits succeed, false if do not have enough available permits.
     */
    public boolean tryAcquire(int n) {
        checkCount(n);
        return acquireInternal(n, true) < 0;
    }

    public double permitsPerSecond() {
        return permitsPerSecond;
    }

    private int checkCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("invalid count value: " + count);
        }
        return count;
    }
}
