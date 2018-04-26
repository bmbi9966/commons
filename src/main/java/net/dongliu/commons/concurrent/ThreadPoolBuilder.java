package net.dongliu.commons.concurrent;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * For building ThreadPoolExecutor.
 */
public class ThreadPoolBuilder {
    private int corePoolSize = 0;
    private int maxPoolSize = Integer.MAX_VALUE;
    private Duration keepAliveTime = Duration.ofSeconds(30);
    private boolean allowCoreThreadTimeOut = false;
    private Supplier<BlockingQueue<Runnable>> workingQueue = LinkedBlockingQueue::new;
    private Supplier<ThreadFactory> threadFactory = () -> new NamedThreadFactory("thread-pool-" + poolSeq.incrementAndGet());
    private Supplier<RejectedExecutionHandler> rejectedHandler = ThreadPoolExecutor.AbortPolicy::new;

    private static final AtomicLong poolSeq = new AtomicLong();

    ThreadPoolBuilder() {
    }

    /**
     * Build ThreadPoolExecutor
     *
     * @return the ThreadPoolExecutor
     */
    public ThreadPoolExecutor build() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTime.toMillis(), TimeUnit.MILLISECONDS,
                workingQueue.get(), threadFactory.get(), rejectedHandler.get());
        executor.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
        return executor;
    }

    /**
     * Set corePoolSize and maxPoolSize.
     *
     * @param corePoolSize default 0
     * @param maxPoolSize  default Integer.MAX_VALUE
     * @return self
     */
    public ThreadPoolBuilder poolSize(int corePoolSize, int maxPoolSize) {
        if (corePoolSize < 0 || maxPoolSize < corePoolSize) {
            throw new IllegalArgumentException();
        }
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    /**
     * Time to keep thread when thread count max than corePoolSize. Default 30 seconds.
     *
     * @param keepAliveTime the keep alive time
     * @return self
     */
    public ThreadPoolBuilder keepAliveTime(Duration keepAliveTime) {
        this.keepAliveTime = requireNonNull(keepAliveTime);
        return this;
    }

    /**
     * Provide working Queue. Default use unlimited LinkedBlockingQueue.
     *
     * @param workingQueue the workingQueue for thread pool
     * @return self
     */
    public ThreadPoolBuilder workingQueue(BlockingQueue<Runnable> workingQueue) {
        requireNonNull(workingQueue);
        this.workingQueue = () -> workingQueue;
        return this;
    }

    /**
     * Provide threadFactory. Default use NamedThreadFactory.
     *
     * @param threadFactory the threadFactory
     * @return self
     */
    public ThreadPoolBuilder threadFactory(ThreadFactory threadFactory) {
        requireNonNull(threadFactory);
        this.threadFactory = () -> threadFactory;
        return this;
    }

    /**
     * Provide Reject Handler. default use ThreadPoolExecutor.AbortPolicy.
     *
     * @param rejectedHandler the Reject Handler
     * @return self
     */
    public ThreadPoolBuilder rejectedHandler(RejectedExecutionHandler rejectedHandler) {
        requireNonNull(rejectedHandler);
        this.rejectedHandler = () -> rejectedHandler;
        return this;
    }

    /**
     * If true, core threads use keepAliveTime to time out waiting for work. Default false
     */
    public ThreadPoolBuilder allowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
        return this;
    }
}
