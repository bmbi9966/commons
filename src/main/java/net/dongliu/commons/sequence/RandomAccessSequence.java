package net.dongliu.commons.sequence;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/**
 * Sequence backend by an random accessed List.
 *
 * @param <T>
 */
class RandomAccessSequence<T> implements Sequence<T> {
    private final List<T> list;
    private final int end;
    private int index;

    RandomAccessSequence(List<T> list) {
        this(list, 0, list.size());
    }

    RandomAccessSequence(List<T> list, int begin, int end) {
        if (!(list instanceof RandomAccess)) {
            throw new IllegalArgumentException("list not random access");
        }
        this.list = list;
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
        return list.get(index++);
    }


}
