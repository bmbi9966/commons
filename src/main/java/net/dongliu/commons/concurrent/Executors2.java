package net.dongliu.commons.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        return newFixedThreadPool(poolSize, queueSize, newNamedThreadFactory(threadNamePrefix),
                new ThreadPoolExecutor.CallerRunsPolicy());
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
        return new ThreadPoolExecutor(poolSize, poolSize, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize),
                threadFactory, handler);
    }

    /**
     * Create a thread factory, with thread name as pattern $prefix-thread-$seq.
     * The Thread Priority is set to NORMAL, Daemon is set to false, and use ThreadGroup as current thread.
     *
     * @param prefix the thread name prefix
     * @return Thread Factory
     */
    public static ThreadFactory newNamedThreadFactory(String prefix) {
        return new NamedThreadFactory(requireNonNull(prefix));
    }

    /**
     * ThreadFactory that can set thread prefix name, each thread has name $prefix-thread-$seq.
     */
    private static class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger threadSeq = new AtomicInteger(1);
        private final String namePrefix;

        public NamedThreadFactory(String prefix) {
            this.namePrefix = requireNonNull(prefix) + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, namePrefix + threadSeq.getAndIncrement());
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
