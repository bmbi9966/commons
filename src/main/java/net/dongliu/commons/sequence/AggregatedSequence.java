package net.dongliu.commons.sequence;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

/**
 * A Sequence aggregate multi(maybe infinite) sub Sequences.
 *
 * @param <T>
 */
class AggregatedSequence<T> implements Sequence<T> {
    private final Iterator<? extends Sequence<T>> sequences;
    private Sequence<T> current = Sequence.of();

    AggregatedSequence(Iterator<? extends Sequence<T>> sequences) {
        this.sequences = sequences;
    }

    @Override
    public boolean hasNext() {
        while (!current.hasNext()) {
            if (!sequences.hasNext()) {
                return false;
            }
            current = requireNonNull(sequences.next());
        }
        return true;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return current.next();
    }
}
