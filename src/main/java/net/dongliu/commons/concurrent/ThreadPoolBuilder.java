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
    private Supplier<ThreadFactory> threadFactory = () -> ThreadFactories.newDaemonThreadFactory("thread-pool-" + poolSeq.incrementAndGet());
    private Supplier<RejectedExecutionHandler> rejectedHandler = ThreadPoolExecutor.AbortPolicy::new;
    private TaskExceptionListener taskExceptionListener = null;

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
                workingQueue.get(), threadFactory.get(), rejectedHandler.get()) {
            private final TaskExceptionListener listener = ThreadPoolBuilder.this.taskExceptionListener;

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                if (listener == null) {
                    return;
                }
                if (t == null && r instanceof Future<?> && ((Future<?>) r).isDone()) {
                    try {
                        ((Future<?>) r).get();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (CancellationException e) {
                        t = e;
                    } catch (ExecutionException e) {
                        t = e.getCause();
                    }
                }
                if (t != null) {
                    listener.onException(r, t);
                }
            }
        };
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

    /**
     * Set Exception listener for this thread pool.
     * <p></p>
     * Note that this do not change the exception handler of the ThreadPool, or the Thread that run the task.
     * If If the task is submit using {@link Executor#execute(Runnable)}, exception will be handler by
     * <ul>
     * <li>Thread's ExceptionHandler if exits;</li>
     * <li>ThreadGroup's ExceptionHandler if exists;</li>
     * <li>The default ExceptionHandler</li>
     * </ul>
     * If noting is set, the default handler will stop the thread, and print exception.
     * <p></p>
     * If the task is submit using {@link ExecutorService#submit(Runnable)}, or {@link ExecutorService#submit(Callable)},
     * The exception will be caught and set to Future, the thread is leaved unaffected.
     *
     * @param listener the exception listener
     * @return self
     */
    public ThreadPoolBuilder taskExceptionListener(TaskExceptionListener listener) {
        this.taskExceptionListener = requireNonNull(listener);
        return this;
    }

    /**
     * Listener that receive the exception of failed task.
     * Note that this do not change the exception handler of the ThreadPool, or the Thread that run the task.
     */
    public interface TaskExceptionListener {
        /**
         * Get called when task exception throw, passing a runnable task, and the exception.
         * If this method throw exception, the thread will be die.
         *
         * @param runnable  the task. If the task is submit using {@link Executor#execute(Runnable)}, this is the runnable it'self;
         *                  If the task is submit using {@link ExecutorService#submit(Runnable)}, or {@link ExecutorService#submit(Callable)},
         *                  this is a FutureTask Object wrapping the original Callable/Runnable.
         * @param throwable the exception thrown by task
         */
        void onException(Runnable runnable, Throwable throwable);
    }
}
