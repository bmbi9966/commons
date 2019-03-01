package net.dongliu.commons.sequence;

abstract class InfiniteSequence<T> implements Sequence<T> {
    @Override
    public boolean hasNext() {
        return true;
    }
}
