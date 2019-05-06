package net.dongliu.commons.sequence;

import java.util.NoSuchElementException;

/**
 * A Sequence only has one element
 */
class SingletonSequence<T> implements Sequence<T> {
    private final T value;
    private int index;

    SingletonSequence(T value) {
        this.value = value;
    }

    @Override
    public boolean hasNext() {
        return index == 0;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
        return value;
    }
}
