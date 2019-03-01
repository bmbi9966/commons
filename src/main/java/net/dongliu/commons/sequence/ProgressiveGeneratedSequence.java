package net.dongliu.commons.sequence;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * A Sequence generate next value according to current value
 *
 * @param <T>
 */
class ProgressiveGeneratedSequence<T> extends InfiniteSequence<T> {
    private final Supplier<T> initial;
    private final UnaryOperator<T> generator;
    private T value;
    private boolean first;

    public ProgressiveGeneratedSequence(Supplier<T> initial, UnaryOperator<T> generator) {
        this.initial = initial;
        this.generator = generator;
        first = true;
    }

    @Override
    public T next() {
        if (first) {
            first = false;
            this.value = initial.get();
        } else {
            this.value = generator.apply(value);
        }
        return this.value;
    }
}
