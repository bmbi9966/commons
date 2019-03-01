package net.dongliu.commons.sequence;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

class FilterSequence<T> implements Sequence<T> {
    private final Sequence<T> sequence;
    private final Predicate<? super T> filter;
    private T value;
    private boolean exists;

    public FilterSequence(Sequence<T> sequence, Predicate<? super T> filter) {
        this.sequence = sequence;
        this.filter = filter;
    }

    @Override
    public boolean hasNext() {
        if (exists) {
            return true;
        }
        while (sequence.hasNext()) {
            var value = sequence.next();
            if (filter.test(value)) {
                exists = true;
                this.value = value;
                return true;
            }
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T value = this.value;
        this.value = null;
        this.exists = false;
        return value;
    }
}
