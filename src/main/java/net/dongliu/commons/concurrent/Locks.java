package net.dongliu.commons.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Utils for Lock.
 */
public class Locks {

    /**
     * Make Lock works better with try-with-resource management. Usage:
     * <pre>
     * try (LockResource c = Locks.with(lock)) {
     *     // do something
     * }
     * </pre>
     * <p>
     * For Java9+:
     *
     * <pre>
     * try (Locks.with(lock)) {
     *     // do something
     * }
     * </pre>
     *
     * @param lock the lock
     * @return
     */
    public static LockResource with(Lock lock) {
        requireNonNull(lock);
        LockResource lockResource = new LockResource(lock);
        lockResource.acquire();
        return lockResource;
    }

    /**
     * Make ReadWriteLock works better with try-with-resource management. Usage:
     * <pre>
     * try (LockResource c = Locks.withRead(lock)) {
     *     // do something
     * }
     * </pre>
     * <p>
     * For Java9+:
     *
     * <pre>
     * try (Locks.withRead(lock)) {
     *     // do something
     * }
     * </pre>
     *
     * @param lock the ReadWriteLock
     * @return
     * @see #withWrite(ReadWriteLock)
     */
    public static LockResource withRead(ReadWriteLock lock) {
        requireNonNull(lock);
        LockResource lockResource = new LockResource(lock.readLock());
        lockResource.acquire();
        return lockResource;
    }

    /**
     * Make ReadWriteLock works better with try-with-resource management. Usage:
     * <pre>
     * try (LockResource c = Locks.withWrite(lock)) {
     *     // do something
     * }
     * </pre>
     * <p>
     * For Java9+:
     *
     * <pre>
     * try (Locks.withWrite(lock)) {
     *     // do something
     * }
     * </pre>
     *
     * @param lock the ReadWriteLock
     * @return
     * @see #withRead(ReadWriteLock)
     */
    public static LockResource withWrite(ReadWriteLock lock) {
        requireNonNull(lock);
        LockResource lockResource = new LockResource(lock.writeLock());
        lockResource.acquire();
        return lockResource;
    }


    /**
     * For auto managing lock resource.
     * Should use value type when we have valhalla.
     */
    public static class LockResource implements AutoCloseable {
        private final Lock lock;

        private LockResource(Lock lock) {
            this.lock = lock;
        }

        // do not put lock operation in constructor, jvm still cannot inline construct well.
        private void acquire() {
            this.lock.lock();
        }

        @Override
        public void close() {
            lock.unlock();
        }
    }

    /**
     * Make lock works better using lambda.
     *
     * @param lock     the lock
     * @param runnable the code to be run
     */
    public static void runWith(Lock lock, Runnable runnable) {
        requireNonNull(lock);
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Make lock works better using lambda.
     *
     * @param lock     the lock
     * @param supplier the code to be run
     */
    public static <T> T callWith(Lock lock, Supplier<T> supplier) {
        requireNonNull(lock);
        lock.lock();
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }
}
