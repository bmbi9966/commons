package net.dongliu.commons.sequence;

import java.util.function.Function;

class MappedSequence<T, R> implements Sequence<R> {
    private final Sequence<T> sequence;
    private final Function<? super T, ? extends R> mapper;

    MappedSequence(Sequence<T> sequence, Function<? super T, ? extends R> mapper) {
        this.sequence = sequence;
        this.mapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return sequence.hasNext();
    }

    @Override
    public R next() {
        return mapper.apply(sequence.next());
    }
}
