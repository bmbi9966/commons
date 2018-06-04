package net.dongliu.commons.concurrent;

import org.jetbrains.annotations.Nullable;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;

/**
 * For creating custom thread-factory
 */
public class ThreadFactoryBuilder {
    private boolean daemon = false;
    private int priority = Thread.NORM_PRIORITY;
    private String name;
    private ThreadNameBuilder threadNameBuilder = ((poolName, threadSeq) -> poolName + "-worker-" + threadSeq);
    @Nullable
    private UncaughtExceptionHandler uncaughtExceptionHandler;
    @Nullable
    private ClassLoader classLoader;

    /**
     * Create a new ThreadFactory with name.
     *
     * @param name the thread Factory name
     */
    ThreadFactoryBuilder(String name) {
        this.name = requireNonNull(name);
    }

    /**
     * Set the daemon property of created thread. Default false.
     *
     * @param daemon thread daemon
     * @return self
     */
    public ThreadFactoryBuilder daemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    /**
     * Set the priority of created thread. Default {@link Thread#NORM_PRIORITY}.
     *
     * @param priority thread priority
     * @return self
     */
    public ThreadFactoryBuilder priority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Set the name for this thread factory.
     *
     * @param name the thread factory name
     * @return self
     */
    public ThreadFactoryBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set uncaughtExceptionHandler for the thread this factory created.
     *
     * @param uncaughtExceptionHandler the uncaughtExceptionHandler, not null
     * @return self
     */
    public ThreadFactoryBuilder uncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = requireNonNull(uncaughtExceptionHandler);
        return this;
    }

    /**
     * Set context ClassLoader for thread created.
     *
     * @param classLoader the context ClassLoader, not null
     * @return self
     */
    public ThreadFactoryBuilder classLoader(ClassLoader classLoader) {
        this.classLoader = requireNonNull(classLoader);
        return this;
    }

    /**
     * Set thread name builder for this thread factory.
     *
     * @param threadNameBuilder thread name builder
     * @return self
     */
    public ThreadFactoryBuilder threadNameBuilder(ThreadNameBuilder threadNameBuilder) {
        this.threadNameBuilder = threadNameBuilder;
        return this;
    }

    /**
     * Build a thread factory.
     *
     * @return the thread factory
     */
    public ThreadFactory build() {
        return new DefaultThreadFactory(name, threadNameBuilder, daemon, priority,
                uncaughtExceptionHandler, classLoader);
    }

    /**
     * Interface for construct Thread name
     */
    @FunctionalInterface
    interface ThreadNameBuilder {
        /**
         * Construct a thread name.
         *
         * @param factoryName the thread factory name
         * @param threadSeq   the sequence of thread in the thread pool, start with 0.
         * @return the thread name
         */
        String getThreadName(String factoryName, long threadSeq);
    }

    /**
     * ThreadFactory that used with ThreadFactoryBuilder.
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        private final AtomicLong threadSeq = new AtomicLong();
        private final String name;
        private final boolean daemon;
        private final int priority;
        private final ThreadNameBuilder threadNameBuilder;
        @Nullable
        private final UncaughtExceptionHandler uncaughtExceptionHandler;
        @Nullable
        private final ClassLoader contextClassLoader;

        protected DefaultThreadFactory(String name, ThreadNameBuilder threadNameBuilder,
                                       boolean daemon, int priority,
                                       @Nullable UncaughtExceptionHandler uncaughtExceptionHandler,
                                       @Nullable ClassLoader contextClassLoader) {
            this.name = requireNonNull(name);
            this.threadNameBuilder = requireNonNull(threadNameBuilder);
            this.daemon = daemon;
            this.priority = priority;
            this.uncaughtExceptionHandler = uncaughtExceptionHandler;
            this.contextClassLoader = contextClassLoader;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, threadNameBuilder.getThreadName(name, threadSeq.getAndIncrement()));
            if (t.isDaemon() != daemon) {
                t.setDaemon(daemon);
            }
            if (t.getPriority() != priority) {
                t.setPriority(priority);
            }
            if (uncaughtExceptionHandler != null) {
                t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            }
            if (contextClassLoader != null) {
                t.setContextClassLoader(contextClassLoader);
            }
            return t;
        }
    }
}
