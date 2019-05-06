package net.dongliu.commons.sequence;

import java.util.Iterator;

class SequenceIterator<T> implements Iterator<T> {
    private final Sequence<T> sequence;

    public SequenceIterator(Sequence<T> sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean hasNext() {
        return sequence.hasNext();
    }

    @Override
    public T next() {
        return sequence.next();
    }
}
