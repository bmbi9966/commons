package net.dongliu.commons.collection;

import java.io.Serializable;
import java.util.Objects;

/**
 * Immutable Tuple3.
 */
public class Triple<A, B, C> implements Serializable {
    private static final long serialVersionUID = -6791546755492843197L;
    private final A first;
    private final B second;
    private final C third;

    private Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Create new Triple. The values cannot be null
     */
    public static <A, B, C> Triple<A, B, C> of(A first, B second, C third) {
        return new Triple<>(first, second, third);
    }

    /**
     * Create one new tuple, replace first value with new value.
     */
    public Triple<A, B, C> withFirst(A first) {
        return new Triple<>(first, second, third);
    }

    /**
     * Create one new tuple, replace second value with new value.
     */
    public Triple<A, B, C> withSecond(B second) {
        return new Triple<>(first, second, third);
    }

    /**
     * Create one new tuple, replace second value with new value.
     */
    public Triple<A, B, C> withThrid(C third) {
        return new Triple<>(first, second, third);
    }

    /**
     * The first value of this tuple
     */
    public A first() {
        return first;
    }

    /**
     * The second value of this tuple
     */
    public B second() {
        return second;
    }

    /**
     * The third value of this tuple
     */
    public C third() {
        return third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return Objects.equals(first, triple.first) &&
                Objects.equals(second, triple.second) &&
                Objects.equals(third, triple.third);
    }

    @Override
    public int hashCode() {

        return Objects.hash(first, second, third);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + third + ')';
    }
}
