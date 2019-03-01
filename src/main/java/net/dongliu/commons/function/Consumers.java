package net.dongliu.commons.function;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * Utils for Consumer
 */
public class Consumers {

    private static final Consumer doNothing = t -> {};

    /**
     * Return a consumer that do noting.
     */
    @SuppressWarnings("unchecked")
    public static <T> Consumer<T> doNothing() {
        return (Consumer<T>) doNothing;
    }

    /**
     * Adaptor for consuming with index
     */
    public static <T> Consumer<T> adapterIndexed(IndexedConsumer<? super T> consumer) {
        var index = new AtomicLong();
        return value -> consumer.accept(index.getAndIncrement(), value);
    }
}
