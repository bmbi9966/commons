package net.dongliu.commons.sequence;

import java.util.NoSuchElementException;

/**
 * Sequence backend by an array.
 *
 * @param <T>
 */
class ArraySequence<T> implements Sequence<T> {
    private final T[] array;
    private final int end;
    private int index;

    ArraySequence(T[] array) {
        this(array, 0, array.length);
    }

    ArraySequence(T[] array, int begin, int end) {
        this.array = array;
        this.end = end;
        this.index = begin;
    }

    @Override
    public boolean hasNext() {
        return index < end;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return array[index++];
    }

}
