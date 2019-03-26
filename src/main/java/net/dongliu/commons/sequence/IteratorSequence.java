package net.dongliu.commons.sequence;

import java.util.Iterator;

/**
 * A Sequence wrap a Iterator.
 *
 * @param <T>
 */
class IteratorSequence<T> implements Sequence<T> {
    private final Iterator<T> iterator;

    IteratorSequence(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }


}
