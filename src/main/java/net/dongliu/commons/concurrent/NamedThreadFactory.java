package net.dongliu.commons.concurrent;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.requireNonNull;

/**
 * ThreadFactory that can set thread prefix name, each thread has name $prefix-thread-$seq.
 */
class NamedThreadFactory implements ThreadFactory {
    private final AtomicInteger threadSeq = new AtomicInteger(1);
    private final String namePrefix;

    public NamedThreadFactory(String prefix) {
        this.namePrefix = requireNonNull(prefix) + "-thread-";
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
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