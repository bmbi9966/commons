package net.dongliu.commons.sequence;

import net.dongliu.commons.collection.Pair;

class ZippedSequence<S, T> implements Sequence<Pair<S, T>> {
    private final Sequence<S> s1;
    private final Sequence<T> s2;

    public ZippedSequence(Sequence<S> s1, Sequence<T> s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public boolean hasNext() {
        return s1.hasNext() && s2.hasNext();
    }

    @Override
    public Pair<S, T> next() {
        return Pair.of(s1.next(), s2.next());
    }
}
