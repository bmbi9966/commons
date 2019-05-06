package net.dongliu.commons.sequence;

import java.util.NoSuchElementException;

/**
 * Empty Sequence
 *
 * @param <T>
 */
class EmptySequence<T> implements Sequence<T> {
    static final EmptySequence instance = new EmptySequence();

    private EmptySequence() {
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        throw new NoSuchElementException();
    }
}
