package net.dongliu.commons.sequence;

import java.util.NoSuchElementException;

class SliceSequence<T> implements Sequence<T> {
    private final Sequence<T> sequence;
    private final long from;
    private final long to;
    private long index = 0;

    SliceSequence(Sequence<T> sequence, long from, long to) {
        this.sequence = sequence;
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean hasNext() {
        while (index < from) {
            if (!sequence.hasNext()) {
                return false;
            }
            if (index >= to) {
                return false;
            }
            sequence.next();
            index++;
        }
        if (index >= to) {
            return false;
        }
        return sequence.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T value = sequence.next();
        index++;
        return value;
    }
}
