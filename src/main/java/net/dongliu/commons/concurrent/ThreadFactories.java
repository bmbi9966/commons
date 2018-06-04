package net.dongliu.commons.concurrent;

import java.util.concurrent.ThreadFactory;

import static java.util.Objects.requireNonNull;

/**
 * Utils method for ThreadFactory
 */
public class ThreadFactories {

    /**
     * Create a new ThreadFactoryBuilder.
     *
     * @return ThreadFactoryBuilder
     */
    public static ThreadFactoryBuilder newBuilder(String name) {
        return new ThreadFactoryBuilder(requireNonNull(name));
    }

    /**
     * Create a thread factory, with thread name as pattern $factoryName-worker-$seq.
     * The Thread Priority is set to NORMAL, Daemon is set to true, and use ThreadGroup as current thread.
     *
     * @param name the thread factory name
     * @return Thread Factory
     */
    public static ThreadFactory newDaemonThreadFactory(String name) {
        requireNonNull(name);
        return ThreadFactories.newBuilder(name).daemon(true).build();
    }
}
