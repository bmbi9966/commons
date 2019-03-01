package net.dongliu.commons.sequence;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

class TakeWhileSequence<T> implements Sequence<T> {
    private final Sequence<T> sequence;
    private final Predicate<? super T> predicate;
    private boolean meet = true;
    private T value;

    public TakeWhileSequence(Sequence<T> sequence, Predicate<? super T> predicate) {
        this.sequence = sequence;
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        if (!meet) {
            return false;
        }
        if (this.value != null) {
            return true;
        }
        if (sequence.hasNext()) {
            T value = sequence.next();
            if (predicate.test(value)) {
                this.value = value;
                return true;
            } else {
                meet = false;
                return false;
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
        return value;
    }
}
