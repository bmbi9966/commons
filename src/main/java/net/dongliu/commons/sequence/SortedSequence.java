package net.dongliu.commons.sequence;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A lazy sorted Sequence implementation
 */
class SortedSequence<T> implements Sequence<T> {
    private Sequence<T> original;
    private final Comparator<? super T> comparator;

    private Sequence<T> delegate = null;

    SortedSequence(Sequence<T> original, Comparator<? super T> comparator) {
        this.original = original;
        this.comparator = comparator;
    }


    @Override
    public boolean hasNext() {
        if (delegate == null) {
            if (!original.hasNext()) {
                return false;
            }
            var list = original.toArrayList();
            if (list.size() == 1) {
                // check if element is illegal for sort ASAP.
                Comparator value = (Comparator) Objects.requireNonNull(list.get(0));
                delegate = Sequence.of(list.get(0));
            } else {
                list.sort(comparator);
                delegate = Sequence.of(list);
            }
            original = null;
        }

        return delegate.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return delegate.next();
    }
}
