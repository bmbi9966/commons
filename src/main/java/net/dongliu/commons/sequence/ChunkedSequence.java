package net.dongliu.commons.sequence;

import java.util.ArrayList;
import java.util.List;

class ChunkedSequence<T> implements Sequence<List<T>> {
    private final Sequence<T> sequence;
    private final int size;

    public ChunkedSequence(Sequence<T> sequence, int size) {
        this.sequence = sequence;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return sequence.hasNext();
    }

    @Override
    public List<T> next() {
        var list = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            if (!sequence.hasNext()) {
                break;
            }
            list.add(sequence.next());
        }
        return List.copyOf(list);
    }
}
