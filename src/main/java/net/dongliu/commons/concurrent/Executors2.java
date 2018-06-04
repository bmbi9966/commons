package net.dongliu.commons.concurrent;

import java.time.Duration;
import java.util.concurrent.*;

import static java.util.Objects.requireNonNull;

/**
 * Utils methods for Executor
 */
public class Executors2 {

    /**
     * Create a new thread pool with fixed pool size, and max task queue size.
     * When task queue is full, run task on current thread, this will block current thread, prevent from submitting more tasks.
     * The Thread name are set as as pattern $threadNamePrefix-thread-$seq.
     *
     * @param poolSize         fixed pool size
     * @param queueSize        max task queue size
     * @param threadNamePrefix the thread name prefix
     * @return ThreadPool
     */
    public static ExecutorService newFixedThreadPool(int poolSize, int queueSize, String threadNamePrefix) {
        return newFixedThreadPool(poolSize, queueSize, ThreadFactories.newDaemonThreadFactory(threadNamePrefix));
    }

    /**
     * Create a new thread pool with fixed pool size, and max task queue size.
     * When task queue is full, run task on current thread, this will block current thread, prevent from submitting more tasks.
     *
     * @param poolSize      fixed pool size
     * @param queueSize     max task queue size
     * @param threadFactory for create thread
     * @return ThreadPool
     */
    public static ExecutorService newFixedThreadPool(int poolSize, int queueSize, ThreadFactory threadFactory) {
        return newFixedThreadPool(poolSize, queueSize, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * Create a new thread pool with fixed pool size, and max task queue size.
     *
     * @param poolSize      fixed pool size
     * @param queueSize     max task queue size
     * @param threadFactory for create thread
     * @param handler       handler when queue is full
     * @return ThreadPool
     */
    public static ExecutorService newFixedThreadPool(int poolSize, int queueSize, ThreadFactory threadFactory,
                                                     RejectedExecutionHandler handler) {
        return new ThreadPoolBuilder().poolSize(poolSize, poolSize)
                .workingQueue(new ArrayBlockingQueue<>(queueSize))
                .threadFactory(threadFactory)
                .rejectedHandler(handler)
                .build();
    }

    /**
     * Return a new ThreadPoolBuilder
     *
     * @return ThreadPoolBuilder
     */
    public static ThreadPoolBuilder threadPoolBuilder() {
        return new ThreadPoolBuilder();
    }

    /**
     * Shutdown Executor and wait task finished.
     *
     * <p>
     * Previously submitted tasks are executed, but no new tasks will be accepted.
     * This method blocks until all tasks have completed execution after a shutdown request,
     * or the timeout occurs, or the current thread is interrupted, whichever happens first.
     * If thread is interrupted, or timeout occurred, will stop all executing tasks by interrupt thread, and cancel
     * all waiting tasks.
     * </p>
     *
     * @param executor the executor to terminate
     * @param timeout  the timeout duration to wait tasks finished.
     */
    public static void shutdownAndAwait(ExecutorService executor, Duration timeout) {
        requireNonNull(executor);
        requireNonNull(timeout);
        executor.shutdown();
        try {
            if (!executor.awaitTermination(timeout.toNanos(), TimeUnit.NANOSECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
