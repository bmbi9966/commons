package net.dongliu.commons.sequence;

import java.util.function.Consumer;

/**
 * A Sequence, with has a side effect when a element is take out, it is consume by specified consumer.
 */
class PeekSequence<T> implements Sequence<T> {

    private final Sequence<T> sequence;
    private final Consumer<? super T> consumer;

    PeekSequence(Sequence<T> sequence, Consumer<? super T> consumer) {
        this.sequence = sequence;
        this.consumer = consumer;
    }

    @Override
    public boolean hasNext() {
        return sequence.hasNext();
    }

    @Override
    public T next() {
        T value = sequence.next();
        consumer.accept(value);
        return value;
    }

}
