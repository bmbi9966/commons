package net.dongliu.commons.collection;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Immutable Tuple3. The values stored in this tuple cannot be null.
 * This is the max tuple we provide. Usually, you should not use this class, define a Data Class for you need.
 */
public class Triple<A, B, C> implements Serializable {
    @NotNull
    private final A first;
    @NotNull
    private final B second;
    @NotNull
    private final C third;

    private Triple(A first, B second, C third) {
        this.first = requireNonNull(first);
        this.second = requireNonNull(second);
        this.third = requireNonNull(third);
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
    public Triple<A, B, C> withFirst(@NotNull A first) {
        return new Triple<>(first, second, third);
    }

    /**
     * Create one new tuple, replace second value with new value.
     */
    public Triple<A, B, C> withSecond(@NotNull B second) {
        return new Triple<>(first, second, third);
    }

    /**
     * Create one new tuple, replace second value with new value.
     */
    public Triple<A, B, C> withThrid(@NotNull C third) {
        return new Triple<>(first, second, third);
    }

    /**
     * The first value of this tuple
     */
    @NotNull
    public A first() {
        return first;
    }

    /**
     * The second value of this tuple
     */
    @NotNull
    public B second() {
        return second;
    }

    /**
     * The third value of this tuple
     */
    @NotNull
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
