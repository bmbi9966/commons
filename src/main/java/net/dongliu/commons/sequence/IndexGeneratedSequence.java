package net.dongliu.commons.sequence;

import java.util.function.LongFunction;

/**
 * A Sequence generated according to index
 *
 * @param <T>
 */
class IndexGeneratedSequence<T> extends InfiniteSequence<T> {
    private final LongFunction<T> generator;
    private long index;

    public IndexGeneratedSequence(LongFunction<T> generator) {
        this.generator = generator;
        index = 0;
    }

    @Override
    public T next() {
        return generator.apply(index++);
    }
}
