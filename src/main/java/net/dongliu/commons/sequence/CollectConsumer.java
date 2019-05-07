package net.dongliu.commons.sequence;

import java.util.function.Consumer;

/**
 * Interface to consume elements, and return a result finally.
 *
 * @param <T> the element type
 * @param <R> the return type
 */
public interface CollectConsumer<T, R> extends Consumer<T> {

    /**
     * Consume value, may be called zero, one, or multi time.
     */
    void accept(T value);

    /**
     * Elements emit finished. This method is called only once, after all elements are send.
     * This method should be called only once.
     *
     * @return should return the final value.
     */
    R finish();
}
