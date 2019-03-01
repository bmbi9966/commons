package net.dongliu.commons.function;

/**
 * A consumer with index.
 */
@FunctionalInterface
public interface IndexedConsumer<T> {

    /**
     * consume value
     *
     * @param index the index of elements consumerd.start from 0
     * @param value the value
     */
    void accept(long index, T value);
}
