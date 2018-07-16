package net.dongliu.commons.collection;

/**
 * A Consumer, sho knows if the value is the last one send to this consumer.
 *
 * @param <T> the value type
 */
public interface LastElementAwareConsumer<T> {
    /**
     * Called for each element in collection
     *
     * @param value the element value
     * @param last  if is last element
     */
    void on(T value, boolean last);
}
