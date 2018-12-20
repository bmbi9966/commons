package net.dongliu.commons.collection;

/**
 * A Consumer, receive element with a index
 *
 * @param <T> the value type
 */
public interface IndexedElementConsumer<T> {
    /**
     * Called when meet a new element
     *
     * @param index the index of element, start at 0
     * @param value the element value
     */
    void on(int index, T value);
}
