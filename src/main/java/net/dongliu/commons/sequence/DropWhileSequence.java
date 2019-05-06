package net.dongliu.commons.sequence;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

class DropWhileSequence<T> implements Sequence<T> {
    private final Sequence<T> sequence;
    private final Predicate<? super T> predicate;
    private boolean meet = true;
    private T firstValue;

    DropWhileSequence(Sequence<T> sequence, Predicate<? super T> predicate) {
        this.sequence = sequence;
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        if (firstValue != null) {
            return true;
        }
        if (meet) {
            while (sequence.hasNext()) {
                T value = sequence.next();
                if (!predicate.test(value)) {
                    firstValue = value;
                    meet = false;
                    return true;
                }
            }
        }
        return sequence.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (firstValue != null) {
            T value = firstValue;
            firstValue = null;
            return value;
        }
        return sequence.next();
    }
}

