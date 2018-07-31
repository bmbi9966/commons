package net.dongliu.commons.concurrent;

import java.lang.ref.SoftReference;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * A weak cache using class as key, and a class-relational processor for class as value.
 * <p>
 * The value is also wrapped in {@link SoftReference}, so even if Processor have strong reference to the class,
 * this cache will not prevent class from unloading when not used any more.
 * Note: use should take care of not holding strong reference to the Class object.
 * This is thread-safe.
 * </p>
 */
public class ClassProcessorLoader<C, T> {
    private final WeakHashMap<Class<? extends C>, SoftReference<T>> map;
    private final ReadWriteLock lock;
    private final Function<Class<? extends C>, ? extends T> processorFactory;

    private ClassProcessorLoader(Function<Class<? extends C>, ? extends T> processorFactory) {
        this.processorFactory = processorFactory;
        this.map = new WeakHashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * Create a new ClassProcessorLoader.
     *
     * @param processorFactory factory to get processor for class
     * @param <C>              the class upper type
     * @param <T>              the processor type
     * @return a ClassProcessorLoader
     */
    public static <C, T> ClassProcessorLoader<C, T> of(Function<Class<? extends C>, ? extends T> processorFactory) {
        return new ClassProcessorLoader<>(processorFactory);
    }

    /**
     * Get processor for class.
     *
     * @param cls not null
     * @return the processor
     */
    public T get(Class<? extends C> cls) {
        lock.readLock().lock();
        try {
            T value = getValue(cls);
            if (value != null) {
                return value;
            }
        } finally {
            lock.readLock().unlock();
        }

        T processor = processorFactory.apply(cls);

        lock.writeLock().lock();
        try {
            this.map.put(cls, new SoftReference<>(processor));
        } finally {
            lock.writeLock().unlock();
        }

        return processor;
    }

    private T getValue(Class<? extends C> cls) {
        SoftReference<T> ref = map.get(cls);
        if (ref == null) {
            return null;
        }
        return ref.get();
    }

}
