package net.dongliu.commons.collection;

/**
 * A Consumer, which knows if the value is the last one send to this consumer.
 *
 * @param <T> the value type
 */
public interface ElementConsumer<T> {
    /**
     * Called when meet a new element
     *
     * @param value the element value
     * @param last  if is last element
     */
    void on(T value, boolean last);
}
