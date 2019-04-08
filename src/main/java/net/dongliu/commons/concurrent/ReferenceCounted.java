package net.dongliu.commons.concurrent;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * Abstract class for reference counted resources.
 *
 * @param <T> the resource type
 */
public abstract class ReferenceCounted<T extends ReferenceCounted<T>> {
    private volatile int count = 1;

    private static final VarHandle COUNT;

    static {
        try {
            COUNT = MethodHandles.lookup()
                    .findVarHandle(ReferenceCounted.class, "count", int.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return current reference count
     */
    public int refCount() {
        return (int) COUNT.getOpaque(this);
    }

    /**
     * Retain the resource, this method make reference count increase by 1.
     *
     * @return self
     */
    public T retain() {
        return retain(1);
    }

    /**
     * Retain the resource, this method make reference count increase by n.
     *
     * @return self
     * @throws IllegalStateException if already destroyed or reference count over flow.
     */
    public T retain(int increment) {
        checkCount(increment);
        int old = (int) COUNT.getAndAdd(this, increment);
        if (old <= 0 || old + increment < old) {
            // Ensure we don't resurrect (which means the refCnt was 0) and also that we encountered an overflow.
            COUNT.getAndAdd(this, -increment);
            throw new IllegalStateException("Illegal retain, current reference count: " + old
                    + ", increase count: " + increment);
        }
        return self();
    }

    /**
     * Release the resource, this method make reference count decrease by 1.
     *
     * @return if destroyed
     */
    public boolean release() {
        return release(1);
    }

    /**
     * Release the resource, this method make reference count decrease by n.
     *
     * @return if destroyed
     * @throws IllegalStateException if already destroyed or reference count under flow.
     */
    public boolean release(int decrement) {
        checkCount(decrement);
        int old = (int) COUNT.getAndAdd(this, -decrement);
        if (old == decrement) {
            destroy();
            return true;
        } else if (old < decrement || old - decrement > old) {
            COUNT.getAndAdd(this, decrement);
            throw new IllegalStateException("Illegal release, current reference count: " + old
                    + ", decrease count: " + decrement);
        }
        return false;
    }


    /**
     * Destroy the resource
     */
    protected abstract void destroy();

    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }

    private static void checkCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count " + count + " should larger than or equal with zero");
        }
    }
}
