package net.dongliu.commons.sequence;

import java.util.function.Supplier;

/**
 * A Sequence generate value by call a provided supplier
 *
 * @param <T>
 */
class SupplierGeneratedSequence<T> extends InfiniteSequence<T> implements Sequence<T> {
    private final Supplier<T> supplier;

    SupplierGeneratedSequence(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T next() {
        return supplier.get();
    }
}
